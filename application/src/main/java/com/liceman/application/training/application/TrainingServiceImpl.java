package com.liceman.application.training.application;

import com.liceman.application.shared.application.loggeduser.LoggedUser;
import com.liceman.application.shared.application.loggeduser.UserContext;
import com.liceman.application.shared.exceptions.TrainingNotExistsException;
import com.liceman.application.training.domain.Comment;
import com.liceman.application.training.domain.Training;
import com.liceman.application.training.domain.TrainingEvent;
import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.training.domain.repository.TrainingEventRepository;
import com.liceman.application.training.domain.repository.TrainingRepository;
import com.liceman.application.training.infrastructure.dto.TrainingCreationRequestDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByAdminDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByMentorDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByUserDTO;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Area;
import com.liceman.application.user.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.liceman.application.training.domain.enums.Status.*;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    public final TrainingRepository trainingRepository;
    public static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private final TrainingEventRepository trainingEventRepository;  // Agrega el repositorio de eventos

    @LoggedUser
    @Override
    public Training createTraining(TrainingCreationRequestDTO trainingCreationRequestDTO) {
        Training newTraining = new Training();
        newTraining.setArea(trainingCreationRequestDTO.getArea());
        newTraining.setTitle(trainingCreationRequestDTO.getTitle());
        newTraining.setUserId(UserContext.getUser());
        newTraining.setCreationDate(LocalDateTime.now());
        newTraining.setStatus(Status.PENDIENTE_MENTOR);

        Comment comment = Comment.builder()
                .training_id(newTraining)
                .user_id(UserContext.getUser())
                .message(trainingCreationRequestDTO.getComment())
                .build();

        newTraining.getComments().add(comment);

        return trainingRepository.save(newTraining);
    }

    @LoggedUser
    @Override
    public List<Training> getTrainings (Integer pageNumber, Integer pageSize, String sortBy, String orderBy) throws IllegalArgumentException {
        Sort sort = Sort.by(sortBy);
        if ("DESC".equalsIgnoreCase(orderBy))
            sort = sort.descending();
        else if ("ASC".equalsIgnoreCase(orderBy))
            sort = sort.ascending();
        else
            throw new IllegalArgumentException("Invalid orderBy value. It should be either ASC or DESC.");
        return getTrainingsAccordingToRole(UserContext.getUser(), PageRequest.of(pageNumber, pageSize, sort));
    }

    private List<Training> getTrainingsAccordingToRole (User user, Pageable pageable) {
        List<Training> trainings;
        if (user.getRole() == Role.USER) {
            trainings =  getAllTrainingsByUser(user, pageable); // Get all trainings created by the logged user
        } else if (user.getRole() == Role.MENTOR) {
            trainings = getAllTrainingsByArea(user.getArea(), pageable); // Get all trainings from the same area of the logged Mentor's area
        } else {
            trainings = getAllTrainings(pageable); // Get all trainings (Admin)
        }
        if(trainings.isEmpty())
            throw new IllegalArgumentException("no hay mas contenido para esta pagina");
        else
            return trainings;
    }

    private List<Training> getAllTrainingsByUser (User user, Pageable pageable) {
        return trainingRepository.findAllByUserIdIs(user, pageable);
    }

    private List<Training> getAllTrainingsByArea (Area area, Pageable pageable) {
        return trainingRepository.findAllByArea(area, pageable);
    }

    private List<Training> getAllTrainings (Pageable pageable) {
        return trainingRepository.findAll(pageable).stream().toList();
    }

    @LoggedUser
    @Override
    public Training getTrainingById (Long id) throws IllegalAccessException {
        User user = UserContext.getUser();
        Role role = user.getRole();
        Training training = getTrainingOrException(id);
        if (role == Role.USER && user.getId().equals(training.getUserId().getId()) ||
                role == Role.MENTOR && training.getArea() == user.getArea() ||
                role == Role.ADMIN) {
            return training;
        } else {
            throw new IllegalAccessException();
        }
    }

    @LoggedUser
    @Override
    public Training mentorUpdateTraining (Long id, UpdateTrainingByMentorDTO request) throws Exception {
        try {
            Training training = getTrainingOrException(id);
            checkValidTrainingStatus(training.getStatus(), PENDIENTE_MENTOR);
            checkValidTrainingMentorArea(training.getArea());
            UpdateTrainingFromMentorRequest(training, request);
            saveTrainingEvent(training, request.getStatus());
            return trainingRepository.save(training);
        } catch (TrainingNotExistsException | IllegalStateException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new IllegalStateException();
        } catch (Exception e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new Exception(e);
        }
    }

    @LoggedUser
    @Override
        public Training userUpdateTraining (Long id, UpdateTrainingByUserDTO request) throws Exception {
        try {
            Training training = getTrainingOrException(id);
            checkValidTrainingStatus(training.getStatus(), PENDIENTE_USER);
            checkValidUser(training.getUserId().getId());
            handledTrainingStatusUpdate(training, request.getStatus(), PENDIENTE_ADMIN);
            saveTrainingEvent(training, request.getStatus());
            return trainingRepository.save(training);
        } catch (TrainingNotExistsException | IllegalStateException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new IllegalStateException();
        } catch (Exception e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new Exception(e);
        }
    }

    @LoggedUser
    @Override
    public Training adminUpdateTraining (Long id, UpdateTrainingByAdminDTO request) throws Exception {
        try {
            Training training = getTrainingOrException(id);
            checkValidTrainingStatus(training.getStatus(), PENDIENTE_ADMIN);
            UpdateTrainingFromAdminRequest(training, request);
            saveTrainingEvent(training, request.getStatus());
            return trainingRepository.save(training);
        } catch (TrainingNotExistsException | IllegalStateException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new IllegalStateException();
        } catch (Exception e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new Exception(e);
        }
    }


    private void UpdateTrainingFromMentorRequest (Training training, UpdateTrainingByMentorDTO request) {
        training.setMentorId(UserContext.getUser());
        Comment comment = Comment.builder()
                .training_id(training)
                .user_id(UserContext.getUser())
                .message(request.getComment())
                .build();

        training.getComments().add(comment);

        training.setDays(request.getDays());
        training.setLink(request.getLink());
        handledTrainingStatusUpdate(training, request.getStatus(), PENDIENTE_USER);
    }


    private void UpdateTrainingFromAdminRequest (Training training, UpdateTrainingByAdminDTO request) {
        training.setApprovedDate(LocalDateTime.now());
        training.setAdminId(UserContext.getUser());
        training.setEndDate(training.getApprovedDate().plusDays(training.getDays()));
        handledTrainingStatusUpdate(training, request.getStatus(), APROBADA);
    }

    private static void checkValidTrainingStatus (Status trainingStatus, Status expectedStatus) {
        if (!trainingStatus.equals(expectedStatus)) {
            throw new IllegalStateException();
        }
    }

    private void checkValidTrainingMentorArea (Area area) {
        if(!area.equals(UserContext.getUser().getArea())){
            throw new IllegalStateException();
        }
    }

    private void checkValidUser (Long userId) {
        if(!userId.equals(UserContext.getUser().getId())){
            throw new IllegalStateException();
        }
    }

    private Training getTrainingOrException (Long id) {
        return trainingRepository.findById(id).orElseThrow(TrainingNotExistsException::new);
    }

    private static void handledTrainingStatusUpdate (Training training, Status requestStatus, Status expectedStatus) {
        if (requestStatus.equals(expectedStatus)) {
            training.setStatus(requestStatus);
        } else {
            training.setStatus(RECHAZADA);
        }
    }

    private void saveTrainingEvent(Training training, Status newStatus) {
        TrainingEvent event = TrainingEvent.builder()
                .training(training)
                .currentStatus(newStatus)
                .user(UserContext.getUser())
                .timestamp(LocalDateTime.now())
                .build();

        trainingEventRepository.save(event);
    }


}

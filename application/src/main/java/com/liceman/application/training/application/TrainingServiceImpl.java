package com.liceman.application.training.application;

import com.liceman.application.shared.application.loggeduser.LoggedUser;
import com.liceman.application.shared.application.loggeduser.UserContext;
import com.liceman.application.shared.exceptions.TrainingNotExistsException;
import com.liceman.application.training.domain.Training;
import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.training.domain.repository.TrainingRepository;
import com.liceman.application.training.infrastructure.dto.*;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.liceman.application.training.domain.enums.Status.*;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    public final TrainingRepository trainingRepository;
    public static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

    @LoggedUser
    @Override
    public Training createTraining (TrainingCreationRequestDTO trainingCreationRequestDTO) {
        Training newTraining = new Training();
        newTraining.setArea(trainingCreationRequestDTO.getArea());
        newTraining.setUserComment(trainingCreationRequestDTO.getUserComment());
        newTraining.setUserId(UserContext.getUser());
        newTraining.setCreationDate(LocalDateTime.now());
        newTraining.setStatus(Status.PENDIENTE_MENTOR);
        return trainingRepository.save(newTraining);
    }

    @LoggedUser
    @Override
    public List<Training> getTrainings () {
        return getTrainingsAccordingToRole(UserContext.getUser());
    }

    private List<Training> getTrainingsAccordingToRole (User user) {

        if (user.getRole() == Role.USER) {
            return getAllTrainingsByUser(user); // Get all trainings created by the logged user
        } else if (user.getRole() == Role.MENTOR) {
            return getAllTrainingsByArea(user.getArea()); // Get all trainings from the same area of the logged Mentor's area
        } else {
            return getAllTrainings(); // Get all trainings (Admin)
        }
    }

    private List<Training> getAllTrainingsByUser (User user) {
        return trainingRepository.findAllByUserIdIs(user);
    }

    private List<Training> getAllTrainingsByArea (Area area) {
        return trainingRepository.findAllByArea(area);
    }

    private List<Training> getAllTrainings () {
        return trainingRepository.findAll();
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
        training.setMentorComment(request.getMentorComment());
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


}

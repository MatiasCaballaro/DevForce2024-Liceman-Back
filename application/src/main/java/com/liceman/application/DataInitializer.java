package com.liceman.application;

import com.liceman.application.shared.exceptions.TrainingNotExistsException;
import com.liceman.application.training.domain.Training;
import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.training.domain.repository.TrainingRepository;
import com.liceman.application.training.infrastructure.dto.TrainingCreationRequestDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByMentorDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByUserDTO;
import com.liceman.application.user.application.UserService;
import com.liceman.application.user.domain.enums.Area;
import com.liceman.application.user.domain.repository.UserRepository;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.liceman.application.training.domain.enums.Status.PENDIENTE_USER;
import static com.liceman.application.training.domain.enums.Status.RECHAZADA;
import static com.liceman.application.user.domain.enums.Role.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    @Value("${sample.data}")
    private Boolean sampleData;

    private final UserService userService;

    private final TrainingRepository trainingRepository;

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {


        if (sampleData) {

            System.out.println("Test Data Initializer begins!  ");

            System.out.println("================================================================================");
            System.out.println("USER CREATION");
            System.out.println("================================================================================");

            UserRequestDTO user1 = UserRequestDTO.builder()
                    .firstname("User")
                    .lastname("User")
                    .email("user@mail.com")
                    .password("password")
                    .role(USER)
                    .hasTeams(true)
                    .build();
            System.out.println("User1 token: " + userService.createUser(user1).getAccessToken());

            UserRequestDTO user2 = UserRequestDTO.builder()
                    .firstname("User")
                    .lastname("User")
                    .email("user2@mail.com")
                    .password("password")
                    .role(USER)
                    .hasTeams(true)
                    .build();
            System.out.println("User2 token: " + userService.createUser(user2).getAccessToken());


            UserRequestDTO mentor1 = UserRequestDTO.builder()
                    .firstname("Mentor")
                    .lastname("Mentor")
                    .email("mentor@mail.com")
                    .password("password")
                    .role(MENTOR)
                    .area(Area.BACKEND)
                    .hasTeams(true)
                    .build();
            System.out.println("Mentor1 (BACKEND) token: " + userService.createUser(mentor1).getAccessToken());

            UserRequestDTO mentor2 = UserRequestDTO.builder()
                    .firstname("Mentor")
                    .lastname("Mentor")
                    .email("mentor2@mail.com")
                    .password("password")
                    .role(MENTOR)
                    .area(Area.BACKEND)
                    .hasTeams(true)
                    .build();
            System.out.println("Mentor2 (BACKEND) token: " + userService.createUser(mentor2).getAccessToken());

            UserRequestDTO mentor3 = UserRequestDTO.builder()
                    .firstname("Mentor")
                    .lastname("Mentor")
                    .email("mentor3@mail.com")
                    .password("password")
                    .role(MENTOR)
                    .area(Area.FRONTEND)
                    .hasTeams(true)
                    .build();
            System.out.println("Mentor3 (FRONTEND) token: " + userService.createUser(mentor3).getAccessToken());

            UserRequestDTO admin = UserRequestDTO.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .hasTeams(true)
                    .build();

            System.out.println("Admin token: " + userService.createUser(admin).getAccessToken());


            System.out.println("================================================================================");
            System.out.println("TRAINING CREATION");
            System.out.println("================================================================================");

            TrainingCreationRequestDTO training1 = TrainingCreationRequestDTO.builder()
                    .userComment("Necesito estudiar JAVA")
                    .area(Area.BACKEND)
                    .build();
            System.out.println("Training1 (BACKEND)" + createTraining(training1,user1));

            TrainingCreationRequestDTO training2 = TrainingCreationRequestDTO.builder()
                    .userComment("Necesito estudiar SPRING BOOT")
                    .area(Area.BACKEND)
                    .build();
            System.out.println("Training1 (BACKEND)" + createTraining(training2,user1));

            TrainingCreationRequestDTO training3 = TrainingCreationRequestDTO.builder()
                    .userComment("Necesito estudiar REACT")
                    .area(Area.FRONTEND)
                    .build();
            System.out.println("Training1 (FRONTEND)" + createTraining(training3,user1));

            TrainingCreationRequestDTO training4 = TrainingCreationRequestDTO.builder()
                    .userComment("Necesito estudiar SQL")
                    .area(Area.DATA)
                    .build();
            System.out.println("Training1 (BACKEND)" + createTraining(training4,user2));


            System.out.println("================================================================================");
            System.out.println("TRAINING UPDATED BY MENTOR CREATION");
            System.out.println("================================================================================");

            UpdateTrainingByMentorDTO trainingMentor1= UpdateTrainingByMentorDTO.builder()
                    .mentorComment("Te sugiero que veas el curso de udemy que adjunto en el link. Si estás de acuerdo, " +
                            "acepta la training, o puedes contactarme por Teams. Éxitos!")
                    .link("#")
                    .days(30)
                    .status(Status.PENDIENTE_USER)
                    .build();
            System.out.println("Training1 Actualizada por Mentor1" + updateTrainingMentor(1L,trainingMentor1,mentor1));


            UpdateTrainingByMentorDTO trainingMentor2= UpdateTrainingByMentorDTO.builder()
                    .mentorComment("Te sugiero que veas el curso de udemy que adjunto en el link. Si estás de acuerdo" +
                            "acepta la training, o puedes contactarme por Teams. Éxitos!")
                    .link("#")
                    .days(45)
                    .status(Status.PENDIENTE_USER)
                    .build();
            System.out.println("Training1 Actualizada por Mentor1" + updateTrainingMentor(2L,trainingMentor2,mentor1));


            System.out.println("================================================================================");
            System.out.println("TRAINING UPDATED BY USER CREATION");
            System.out.println("================================================================================");

            //TODO crear
            UpdateTrainingByUserDTO trainingUser1= UpdateTrainingByUserDTO.builder()
                    .status(Status.PENDIENTE_ADMIN)
                    .build();
            System.out.println("Training1 Actualizada por User1" + UpdateUserTrainingDTO(1L,trainingUser1));


            System.out.println("================================================================================");
            System.out.println("TRAINING UPDATED BY ADMIN CREATION");
            System.out.println("================================================================================");

            //TODO crear


            System.out.println("Test Data Initializer finished! ");
        }
    }

    private Training createTraining (TrainingCreationRequestDTO trainingCreationRequestDTO, UserRequestDTO userDTO) {
        Training newTraining = new Training();
        newTraining.setArea(trainingCreationRequestDTO.getArea());
        newTraining.setUserComment(trainingCreationRequestDTO.getUserComment());
        newTraining.setCreationDate(LocalDateTime.now());
        newTraining.setStatus(Status.PENDIENTE_MENTOR);
        newTraining.setUserId(userRepository.findByEmail(userDTO.getEmail()).orElseThrow(NoSuchElementException::new));
        return trainingRepository.save(newTraining);
    }

    private Training updateTrainingMentor (Long id, UpdateTrainingByMentorDTO request, UserRequestDTO userDTO) {
        try {
            Training training = trainingRepository.findById(id).orElseThrow(TrainingNotExistsException::new);
            training.setMentorId(userRepository.findByEmail(userDTO.getEmail()).orElseThrow(NoSuchElementException::new));
            training.setMentorComment(request.getMentorComment());
            training.setDays(request.getDays());
            training.setLink(request.getLink());
            if(request.getStatus().equals(PENDIENTE_USER)){
                training.setStatus(request.getStatus());
            } else{
                training.setStatus(RECHAZADA);
            }
            return trainingRepository.save(training);
        } catch (Exception e) {
            throw new TrainingNotExistsException();
        }
    }

    private Training UpdateUserTrainingDTO (Long id, UpdateTrainingByUserDTO request) {
        try {
            Training training = trainingRepository.findById(id).orElseThrow(TrainingNotExistsException::new);
            training.setStatus(request.getStatus());
            return trainingRepository.save(training);
        } catch (Exception e) {
            throw new TrainingNotExistsException();
        }
    }

}


package com.liceman.application.shared.application.loggeduser;

import com.liceman.application.usuario.domain.User;
import com.liceman.application.usuario.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggedUserAspect {

    private final UserRepository userRepository;

       @Before("@annotation(com.liceman.application.shared.application.loggeduser.LoggedUser)")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = UserContext.getUser();

        if (loggedUser == null) {
            loggedUser = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con el correo electrónico: " + authentication.getName()));
            UserContext.setUser(loggedUser);
        }
    }
}
package com.example.dits.controllers;

import com.example.dits.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SecurityController {
    private final UserService userService;

    @GetMapping("/login-handle")
    public String loginHandle(HttpSession session) {
        session.setAttribute("user", userService.getUserInfoByLogin(getUsername()));
        String authority = getAuthority();
        return authority.contains("USER") ? "redirect:/user/chooseTest" : "redirect:/admin/testBuilder";
    }

    @GetMapping("/login")
    public String loginPage(ModelMap model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/accessDenied")
    public String accessDeniedGet() {
        return "accessDenied";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null)
            new SecurityContextLogoutHandler().logout(request, response, auth);

        return "redirect:/login?logout";
    }

    private static String getUsername() {
        Object principal = getPrincipal();

        return principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
    }

    private static String getAuthority() {
        Object principal = getPrincipal();
        return principal instanceof UserDetails ? String.valueOf(((UserDetails) principal).getAuthorities().stream().findFirst().orElse(null)) : principal.toString();
    }

    private static Object getPrincipal() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

}

package com.softuni.mobilele.web;

import com.softuni.mobilele.domain.dtoS.banding.UserLoginFormDto;
import com.softuni.mobilele.domain.dtoS.banding.UserRegisterFormDto;
import com.softuni.mobilele.domain.dtoS.veiw.UserRoleViewDto;
import com.softuni.mobilele.services.UserRoleService;
import com.softuni.mobilele.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

// annotation -> userRegisterFrom -> Validator password.equals(confirmPassword) true false

@Controller
@RequestMapping("/users") // url after localhost:8080 -> /users
public class UserController extends BaseController {
    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";

    private final UserRoleService roleService;
    private final UserService userService;

    @Autowired
    public UserController(UserRoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    // http mappings

    @GetMapping("/register") // post method localhost:8080/users/register
    public String getRegister(Model model) {
        return "auth-register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute(name = "userRegisterForm") UserRegisterFormDto userRegisterInfo,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterForm", userRegisterInfo)
                    .addFlashAttribute(BINDING_RESULT_PATH + "userRegisterForm", bindingResult);

            return "redirect:register";
        }
        //todo

        return "redirect:login";
    }


    @GetMapping("/login")
    public ModelAndView getLogin() {
        return super.view("auth-login");
    }

    // Model attributes

    @ModelAttribute(name = "userRegisterForm")
    public UserRegisterFormDto initUserRegisterFormDto() {
        return new UserRegisterFormDto();
    }

    @ModelAttribute(name = "userLoginForm")
    public UserLoginFormDto initUserLoginFormDto() {
        return new UserLoginFormDto();
    }

    @ModelAttribute(name = "roles")
    public List<UserRoleViewDto> getAllRoles() {
        return this.roleService.getAll();
    }
}

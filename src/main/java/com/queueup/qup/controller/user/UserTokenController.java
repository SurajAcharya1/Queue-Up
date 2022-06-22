package com.queueup.qup.controller.user;


import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/token")
public class UserTokenController{

    @Autowired
    UserRepo userRepo;
    @Autowired
    LogInController logInController;
    @Autowired
    TokenRepo tokenRepo;
    @GetMapping
    public String openUserTokenPage(Model model){
        try{
            if(userRepo.getRoleByID(logInController.loggedInUserid)==null){
                model.addAttribute("userName",userRepo.findNameById(logInController.loggedInUserid));
                model.addAttribute("tokenNumber", tokenRepo.getTokenNumber(logInController.loggedInUserid));
                return "users/userToken";
            }
            else{
                return "error";
            }
        }catch (Exception e){
            return "error";
        }
    }
}

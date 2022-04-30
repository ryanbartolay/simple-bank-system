package com.ryan.banking.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.ryan.banking.controller.dto.DepositDto;
import com.ryan.banking.controller.dto.TransactionDepositRequestDto;
import com.ryan.banking.controller.dto.TransactionWithdrawRequestDto;
import com.ryan.banking.controller.dto.WithdrawDto;
import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.exception.TransactionException;
import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.service.TransactionService;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/withdraw")
    public RedirectView addBook(@ModelAttribute("withdrawRequest") TransactionWithdrawRequestDto txRequestWithdraw,
            RedirectAttributes redirectAttributes)
            throws TransactionException, UserNotFoundException, AccountNotFoundException {
        WithdrawDto withdrawDto = transactionService.withdraw(txRequestWithdraw);
        final RedirectView redirectView = new RedirectView("/user" + txRequestWithdraw.getUserId(), true);
        redirectAttributes.addFlashAttribute("withdrawSuccess", true);
        return redirectView;
    }

    @PostMapping(value = "/deposit")
    public RedirectView addBook(@ModelAttribute("deposit") TransactionDepositRequestDto txRequestDeposit,
            RedirectAttributes redirectAttributes)
            throws TransactionException, UserNotFoundException, AccountNotFoundException {
        DepositDto depositDto = transactionService.deposit(txRequestDeposit);
        final RedirectView redirectView = new RedirectView("/users/" + txRequestDeposit.getUserId(), true);
        redirectAttributes.addFlashAttribute("depositSuccess", true);
        return redirectView;
    }
}

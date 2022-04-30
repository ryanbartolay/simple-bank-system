package com.ryan.banking.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.ryan.banking.controller.dto.TransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionRequestType;
import com.ryan.banking.controller.dto.TransactionResultDto;
import com.ryan.banking.exception.AccountNotFoundException;
import com.ryan.banking.exception.TransactionException;
import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.service.TransactionService;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/deposit")
    public RedirectView deposit(@ModelAttribute("deposit") TransactionRequestDto txRequestDeposit,
            RedirectAttributes redirectAttributes)
            throws TransactionException, UserNotFoundException, AccountNotFoundException {
        assignTransaction(txRequestDeposit);
        TransactionResultDto depositDto = transactionService.processTransactionRequest(txRequestDeposit);
        final RedirectView redirectView = new RedirectView("/users/" + txRequestDeposit.getUserId(), true);
        redirectAttributes.addFlashAttribute("depositDto", depositDto);
        redirectAttributes.addFlashAttribute("depositSuccess",
                transactionService.isTransactionCompleted(depositDto.getStatus()));
        return redirectView;
    }

    @PostMapping(value = "/withdraw")
    public RedirectView withdraw(@ModelAttribute("withdrawRequest") TransactionRequestDto txRequestWithdraw,
            RedirectAttributes redirectAttributes)
            throws TransactionException, UserNotFoundException, AccountNotFoundException {
        assignTransaction(txRequestWithdraw);
        TransactionResultDto withdrawDto = transactionService.processTransactionRequest(txRequestWithdraw);
        final RedirectView redirectView = new RedirectView("/users/" + txRequestWithdraw.getUserId(), true);
        redirectAttributes.addFlashAttribute("withdrawDto", withdrawDto);
        redirectAttributes.addFlashAttribute("withdrawSuccess",
                transactionService.isTransactionCompleted(withdrawDto.getStatus()));
        return redirectView;
    }

    private void assignTransaction(TransactionRequestDto txRequest) {
        if (ObjectUtils.isEmpty(txRequest) || !StringUtils.hasText(txRequest.getTransaction())) {
            return;
        }
        txRequest.setType(TransactionRequestType.valueOf(StringUtils.capitalize(txRequest.getTransaction())));
    }
}

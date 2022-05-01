package com.ryan.banking.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.ryan.banking.controller.DefaultTransactionController;
import com.ryan.banking.controller.dto.TransactionRequestDto;
import com.ryan.banking.controller.dto.TransactionResultDto;
import com.ryan.banking.model.enums.TransactionStatus;
import com.ryan.banking.service.TransactionService;

@Controller
@RequestMapping("/transaction")
public class TransactionController implements DefaultTransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/deposit")
    public RedirectView deposit(@Valid @ModelAttribute("deposit") TransactionRequestDto txRequestDeposit,
            RedirectAttributes redirectAttributes)
            throws Exception {
        assignTransaction(txRequestDeposit);
        TransactionResultDto depositDto = transactionService.processTransactionRequest(txRequestDeposit);
        return buildRedirectView(txRequestDeposit, depositDto, redirectAttributes);
    }

    @PostMapping(value = "/withdraw")
    public RedirectView withdraw(@Valid @ModelAttribute("withdrawRequest") TransactionRequestDto txRequestWithdraw,
            RedirectAttributes redirectAttributes)
            throws Exception {
        assignTransaction(txRequestWithdraw);
        TransactionResultDto withdrawDto = transactionService.processTransactionRequest(txRequestWithdraw);
        return buildRedirectView(txRequestWithdraw, withdrawDto, redirectAttributes);
    }

    private RedirectView buildRedirectView(TransactionRequestDto transactionRequest,
            TransactionResultDto transactionResultDto, RedirectAttributes redirectAttributes) throws Exception {
        String redirectPath = "/users/" + transactionRequest.getUserId();
        final RedirectView redirectView = new RedirectView(redirectPath, true);
        if (TransactionStatus.CANCELLED.equals(transactionResultDto.getStatus())) {
            // Do nothing
        } else if (TransactionStatus.COMPLETED.equals(transactionResultDto.getStatus())) {
            redirectAttributes.addFlashAttribute("resultDto", transactionResultDto);
            redirectAttributes.addFlashAttribute("transactionSuccess", true);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Successfully " + transactionRequest.getType().toString().toLowerCase() + " : "
                            + transactionResultDto.getAmount());
        } else if (TransactionStatus.INVALID.equals(transactionResultDto.getStatus())) {
            String back = "/users/" + transactionRequest.getUserId() + "/"
                    + transactionRequest.getType().toString().toLowerCase() + "/" + transactionRequest.getAccountId();
            redirectView.setUrl(back);
            redirectAttributes.addFlashAttribute("resultDto", transactionResultDto);
            redirectAttributes.addFlashAttribute("transactionFailed", true);
            redirectAttributes.addFlashAttribute("failedMessage", transactionResultDto.getRemarks());
        } else {
            throw new Exception("This transaction is not handled");
        }
        return redirectView;
    }
}

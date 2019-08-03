package com.bancomo.objects.zipmodels;

import com.bancomo.objects.accounts.loan.LoanWithAssociations;
import com.bancomo.objects.templates.loans.LoanRepaymentTemplate;

/**
 *
 * Created by Rajan Maurya on 08/08/16.
 */
public class LoanAndLoanRepayment {

    LoanWithAssociations loanWithAssociations;
    LoanRepaymentTemplate loanRepaymentTemplate;

    public LoanWithAssociations getLoanWithAssociations() {
        return loanWithAssociations;
    }

    public void setLoanWithAssociations(LoanWithAssociations loanWithAssociations) {
        this.loanWithAssociations = loanWithAssociations;
    }

    public LoanRepaymentTemplate getLoanRepaymentTemplate() {
        return loanRepaymentTemplate;
    }

    public void setLoanRepaymentTemplate(LoanRepaymentTemplate loanRepaymentTemplate) {
        this.loanRepaymentTemplate = loanRepaymentTemplate;
    }

    public LoanAndLoanRepayment() {

    }

    public LoanAndLoanRepayment(LoanWithAssociations loanWithAssociations, LoanRepaymentTemplate
            loanRepaymentTemplate) {
        this.loanWithAssociations = loanWithAssociations;
        this.loanRepaymentTemplate = loanRepaymentTemplate;
    }

    @Override
    public String toString() {
        return "LoanAndLoanRepayment{" +
                "loanWithAssociations=" + loanWithAssociations +
                ", loanRepaymentTemplate=" + loanRepaymentTemplate +
                '}';
    }
}
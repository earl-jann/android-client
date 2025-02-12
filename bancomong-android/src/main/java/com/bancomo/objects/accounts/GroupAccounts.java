/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.objects.accounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.bancomo.objects.accounts.loan.LoanAccount;
import com.bancomo.objects.accounts.savings.SavingsAccount;

import java.util.ArrayList;
import java.util.List;

public class GroupAccounts implements Parcelable {

    private List<LoanAccount> loanAccounts = new ArrayList<LoanAccount>();
    private List<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>();

    public List<LoanAccount> getLoanAccounts() {
        return loanAccounts;
    }

    public void setLoanAccounts(List<LoanAccount> loanAccounts) {
        this.loanAccounts = loanAccounts;
    }

    public GroupAccounts withLoanAccounts(List<LoanAccount> loanAccounts) {
        this.loanAccounts = loanAccounts;
        return this;
    }

    public List<SavingsAccount> getSavingsAccounts() {
        return savingsAccounts;
    }

    public void setSavingsAccounts(List<SavingsAccount> savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }

    public GroupAccounts withSavingsAccounts(List<SavingsAccount> savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
        return this;
    }

    public List<SavingsAccount> getRecurringSavingsAccounts() {
        return getSavingsAccounts(true);
    }

    public List<SavingsAccount> getNonRecurringSavingsAccounts() {
        return getSavingsAccounts(false);
    }

    private List<SavingsAccount> getSavingsAccounts(boolean wantRecurring) {
        List<SavingsAccount> result = new ArrayList<SavingsAccount>();
        if (this.savingsAccounts != null) {
            for (SavingsAccount account : savingsAccounts) {
                if (account.isRecurring() == wantRecurring) {
                    result.add(account);
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "ClientAccounts{" +
                "loanAccounts=" + loanAccounts +
                ", savingsAccounts=" + savingsAccounts +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.loanAccounts);
        dest.writeTypedList(this.savingsAccounts);
    }

    public GroupAccounts() {
    }

    protected GroupAccounts(Parcel in) {
        this.loanAccounts = in.createTypedArrayList(LoanAccount.CREATOR);
        this.savingsAccounts = in.createTypedArrayList(SavingsAccount.CREATOR);
    }

    public static final Parcelable.Creator<GroupAccounts> CREATOR = new Parcelable
            .Creator<GroupAccounts>() {
        @Override
        public GroupAccounts createFromParcel(Parcel source) {
            return new GroupAccounts(source);
        }

        @Override
        public GroupAccounts[] newArray(int size) {
            return new GroupAccounts[size];
        }
    };
}

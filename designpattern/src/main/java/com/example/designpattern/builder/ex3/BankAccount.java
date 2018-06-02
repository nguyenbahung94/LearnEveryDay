package com.example.designpattern.builder.ex3;

/**
 * Created by nbhung on 3/16/2018.
 */

public class BankAccount {
    private long accountNumber; //This is important, so we'll pass it to the constructor.

    private String owner;

    private String branch;

    private double balance;

    private double interestRate;

    public static class Builder {
        private long accountNumber; //This is important, so we'll pass it to the constructor.

        private String owner;

        private String branch;

        private double balance;

        private double interestRate;

        public Builder(Long accountNumber) {
            this.accountNumber = accountNumber;
        }

        public Builder withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder atBranch(String branch) {

            this.branch = branch;
            return this;

        }

        public Builder openingBalance(double balance) {

            this.balance = balance;

            return this;

        }

        public Builder atRate(double interestRate) {

            this.interestRate = interestRate;

            return this;

        }

        public BankAccount build() {

            //Here we create the actual bank account object, which is always in a fully initialised state when it's returned.

            BankAccount account = new BankAccount();  //Since the builder is in the BankAccount class, we can invoke its private constructor.

            account.accountNumber = this.accountNumber;

            account.owner = this.owner;

            account.branch = this.branch;

            account.balance = this.balance;

            account.interestRate = this.interestRate;

            return account;
        }
    }
}

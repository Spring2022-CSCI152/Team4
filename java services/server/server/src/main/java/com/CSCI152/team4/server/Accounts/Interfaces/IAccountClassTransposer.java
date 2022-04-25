package com.CSCI152.team4.server.Accounts.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;

import java.lang.reflect.InvocationTargetException;

public interface IAccountClassTransposer {

    WorkerAccount transposeTo(Class accountType, WorkerAccount account)
            throws InvocationTargetException, InstantiationException, IllegalAccessException;
}

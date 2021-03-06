package com.CSCI152.team4.server.Accounts.Utils;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountTransposer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@SuppressWarnings("ALL")
@Component
public class AccountTransposer implements IAccountTransposer {

    @Override
    public WorkerAccount transposeTo(Class accountType, WorkerAccount account)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor con = null;
        /*Iterate throw class constructors*/
        for(Constructor c : accountType.getDeclaredConstructors()){
            if(c.getParameterCount() == 6){
                con = c;
            }
        }
        /*Create new instance using 6 param constructor*/
        WorkerAccount ret =  (WorkerAccount) Objects.requireNonNull(con).newInstance(account.getBusinessId(),
                account.getEmail(), account.getPassword(),
                account.getFirstName(), account.getLastName(),
                account.getJobTitle());

        /*Set account id to original ID*/
        ret.setAccountId(account.getAccountIdString());
        return ret;
    }
}

package com.romansholokh.tasklist.backendspringboot.util;

public class Logger
{
    public static void printClassMethodName(Thread thread)
    {
        String className = thread.getStackTrace()[2].getClassName();
        String methodName = thread.getStackTrace()[2].getMethodName();
        System.out.println();
        System.out.println();
        System.out.printf("%s:%s()-------------------------------------------------------------------%n",
                className.substring(className.lastIndexOf(".") + 1), methodName);
    }


}

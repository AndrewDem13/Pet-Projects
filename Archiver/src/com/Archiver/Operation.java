package com.javarush.test.level31.lesson15.big01;

public enum Operation {
    CREATE,
    ADD,
    REMOVE,
    EXTRACT,
    CONTENT,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i)
    {
        switch(i)
        {
            case 0: return CREATE;
            case 1: return ADD;
            case 2: return REMOVE;
            case 3: return EXTRACT;
            case 4: return CONTENT;
            case 5: return EXIT;
            default: throw new IllegalArgumentException();
        }
    }
}

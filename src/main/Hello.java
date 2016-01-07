package main;

import instruction.JsonProcess;

/**
 * Created by Badger on 16/1/7.
 */
public class Hello {
    public static void main(String[] args) {

        String outStr = "";
//        outStr = new JsonProcess().getJsonStr(null);
        new JsonProcess().getMoveInfos(null);
        System.out.println(outStr);

    }
}

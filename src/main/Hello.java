package main;

import demo.Instruction;
import instruction.JsonProcess;
import instruction.LoadConfig;
import instruction.UnitPosition;

/**
 * Created by Badger on 16/1/7.
 */
public class Hello {
    public static void main(String[] args) {

        String outStr = "V-LH526002-030002";
//        outStr = new JsonProcess().getJsonStr(null);
        new JsonProcess().getMoveInfos(Instruction.receiveJsonDemo);
//        System.out.println(new UnitPosition(outStr).toString());
        outStr = new JsonProcess().getJsonStr(new LoadConfig().getInstructions());
        System.out.println(outStr);

    }
}

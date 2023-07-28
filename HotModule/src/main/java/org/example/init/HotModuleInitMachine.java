package org.example.init;

import org.example.log.ChopperLogFactory;
import org.example.log.LoggerType;

import java.util.List;

/**
 * @author Genius
 * @date 2023/07/21 00:16
 **/

/**
 * 整个热门模块的模块初始化类
 */
public class HotModuleInitMachine extends ModuleInitMachine{

    public HotModuleInitMachine() {
        super(
                List.of(new HotModuleConfigInitMachine(),new HotModuleGuardInitMachine()),
                "HotModule",
                ChopperLogFactory.getLogger(LoggerType.Hot)
        );
    }
}

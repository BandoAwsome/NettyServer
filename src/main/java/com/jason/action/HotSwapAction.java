package com.jason.action;

import com.sun.tools.attach.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 动更Action
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/7 16:35
 */
@Component("hotswap")
public class HotSwapAction extends ActionBase{

    public String hotswap() {
        try {
            List<VirtualMachineDescriptor> list = VirtualMachine.list();
            for (VirtualMachineDescriptor vmd : list) {
                VirtualMachine virtualMachine = null;
                virtualMachine = VirtualMachine.attach(vmd.id());
                // 获得代理类位置 + 传递参数
                virtualMachine.loadAgent("E:\\agentmain.jar", "hotswap@com.jason.action.PrintAction");
                virtualMachine.detach();
            }
        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AgentLoadException e) {
            e.printStackTrace();
        } catch (AgentInitializationException e) {
            e.printStackTrace();
        }
        return "";
    }
}

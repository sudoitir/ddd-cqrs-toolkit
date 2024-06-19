package io.github.sudoitir.dddcqrstoolkit.cqs.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleRegistry {

    private final List<BeforeExecutionModule> beforeExecutionModules;
    private final List<AfterExecutionModule> afterExecutionModules;
    private final List<AfterThrowingModule> afterThrowingExecutionModules;

    public ModuleRegistry(BusModule... modules) {
        List<BeforeExecutionModule> newBeforeExecutionModules = new ArrayList<>();
        List<AfterExecutionModule> newAfterExecutionModules = new ArrayList<>();
        List<AfterThrowingModule> newAfterThrowingExecutionModules = new ArrayList<>();

        for (BusModule module : modules) {
            if (module instanceof BeforeExecutionModule beforeexecutionmodule) {
                newBeforeExecutionModules.add(beforeexecutionmodule);
            }
            if (module instanceof AfterExecutionModule afterExecutionModule) {
                newAfterExecutionModules.add(afterExecutionModule);
            }
            if (module instanceof AfterThrowingModule afterThrowingModule) {
                newAfterThrowingExecutionModules.add(afterThrowingModule);
            }
        }

        this.beforeExecutionModules = Collections.unmodifiableList(newBeforeExecutionModules);
        this.afterExecutionModules = Collections.unmodifiableList(newAfterExecutionModules);
        this.afterThrowingExecutionModules = Collections.unmodifiableList(
                newAfterThrowingExecutionModules);
    }


    public List<BeforeExecutionModule> getBeforeExecutionModules() {
        return beforeExecutionModules;
    }

    public List<AfterExecutionModule> getAfterExecutionModules() {
        return afterExecutionModules;
    }

    public List<AfterThrowingModule> getAfterThrowingModules() {
        return afterThrowingExecutionModules;
    }
}

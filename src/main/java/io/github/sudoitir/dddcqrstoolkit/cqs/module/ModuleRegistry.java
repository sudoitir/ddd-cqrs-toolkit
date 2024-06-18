package io.github.sudoitir.dddcqrstoolkit.cqs.module;

import java.text.MessageFormat;
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
            if (module instanceof BeforeExecutionModule) {
                registerModule(newBeforeExecutionModules, (BeforeExecutionModule) module);
            } else if (module instanceof AfterExecutionModule) {
                registerModule(newAfterExecutionModules, (AfterExecutionModule) module);
            } else if (module instanceof AfterThrowingModule) {
                registerModule(newAfterThrowingExecutionModules,
                        (AfterThrowingModule) module);
            } else {
                throw new UnsupportedOperationException(
                        MessageFormat.format("Module type {0} not supported!",
                                module.getClass().getSimpleName()));
            }
        }

        this.beforeExecutionModules = Collections.unmodifiableList(newBeforeExecutionModules);
        this.afterExecutionModules = Collections.unmodifiableList(newAfterExecutionModules);
        this.afterThrowingExecutionModules = Collections.unmodifiableList(
                newAfterThrowingExecutionModules);
    }

    private <T extends BusModule> void registerModule(List<T> modules, T moduleToAdd) {
        if (modules.contains(moduleToAdd)) {
            throw new IllegalArgumentException("Module already registered: " + moduleToAdd);
        }
        modules.add(moduleToAdd);
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

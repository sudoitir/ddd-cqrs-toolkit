package io.github.sudoitir.dddcqrstoolkit.cqs.module;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ModuleRegistry {


    private final List<BusModule> modules = new ArrayList<>();

    private List<BeforeExecutionModule> cachedBeforeExecutionModules;
    private List<AfterExecutionModule> cachedAfterExecutionModules;
    private List<AfterThrowingExecutionModule> cachedAfterThrowingExecutionModules;
    private List<BusTypeProvider> cachedBusTypeProviders;

    public ModuleRegistry registerModule(BusModule module) {
        modules.add(module);
        clearCaches();
        return this;
    }

    public List<BusModule> getModules() {
        return modules;
    }

    public List<BeforeExecutionModule> getBeforeExecutionModules() {
        if (cachedBeforeExecutionModules == null) {
            cachedBeforeExecutionModules = modules.stream()
                    .filter(BeforeExecutionModule.class::isInstance)
                    .map(BeforeExecutionModule.class::cast)
                    .toList();
        }
        return cachedBeforeExecutionModules;
    }

    public List<AfterExecutionModule> getAfterExecutionModules() {
        if (cachedAfterExecutionModules == null) {
            cachedAfterExecutionModules = modules.stream()
                    .filter(AfterExecutionModule.class::isInstance)
                    .map(AfterExecutionModule.class::cast)
                    .toList();
        }
        return cachedAfterExecutionModules;
    }

    public List<AfterThrowingExecutionModule> getAfterThrowingModules() {
        if (cachedAfterThrowingExecutionModules == null) {
            cachedAfterThrowingExecutionModules = modules.stream()
                    .filter(AfterThrowingExecutionModule.class::isInstance)
                    .map(AfterThrowingExecutionModule.class::cast)
                    .toList();
        }
        return cachedAfterThrowingExecutionModules;
    }

    public List<BusTypeProvider> getBusTypeProviders() {
        if (cachedBusTypeProviders == null) {
            cachedBusTypeProviders = modules.stream()
                    .filter(BusTypeProvider.class::isInstance)
                    .map(BusTypeProvider.class::cast)
                    .toList();
        }
        return cachedBusTypeProviders;
    }

    private void clearCaches() {
        cachedBeforeExecutionModules = null;
        cachedAfterExecutionModules = null;
        cachedAfterThrowingExecutionModules = null;
        cachedBusTypeProviders = null;
    }
}

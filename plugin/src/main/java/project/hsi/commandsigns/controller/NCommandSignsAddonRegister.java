package project.hsi.commandsigns.controller;

import project.hsi.commandsigns.api.AddonRegister;
import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonLifecycleHooker;
import project.hsi.commandsigns.api.addons.NCSLifecycleHook;
import project.hsi.commandsigns.api.menu.AddonEditionMenu;
import project.hsi.commandsigns.api.menu.AddonSubmenuHolder;
import project.hsi.commandsigns.model.CoreAddonSubmenusHolder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class NCommandSignsAddonRegister implements AddonRegister {

    private final List<Addon> addons = new ArrayList<>(6);
    private boolean enabled = false;

    public NCommandSignsAddonRegister() {
    }

    public void registerAddon(final Addon addon) {
        if (addon != null) {
            addons.add(addon);
        }
    }

    public void triggerEnable() {
        if (!enabled) {
            for (Addon addon : this.addons) {
                addon.onEnable();
            }
            enabled = true;
        }
    }

    public void registerInManager(NCommandSignsManager manager) {
        for (Addon addon : addons) {
            manager.registerAddon(addon);

            registerLifecycle(manager, addon);
            registerSubmenus(manager, addon);
        }
    }

    private void registerSubmenus(NCommandSignsManager manager, Addon addon) {
        AddonSubmenuHolder addonSubmenus = addon.getSubmenus();
        if (addonSubmenus != null) {
            CoreAddonSubmenusHolder registeredSubmenus = manager.getAddonSubmenus();

            if (!addonSubmenus.requirementSubmenus.isEmpty()) {
                List<AddonEditionMenu> menus = registeredSubmenus.requirementSubmenus.computeIfAbsent(addon, (a) -> new LinkedList<>());
                menus.addAll(addonSubmenus.requirementSubmenus);
            }

            if (!addonSubmenus.costSubmenus.isEmpty()) {
                List<AddonEditionMenu> menus = registeredSubmenus.costSubmenus.computeIfAbsent(addon, (a) -> new LinkedList<>());
                menus.addAll(addonSubmenus.costSubmenus);
            }

            if (!addonSubmenus.executionSubmenus.isEmpty()) {
                List<AddonEditionMenu> menus = registeredSubmenus.executionSubmenus.computeIfAbsent(addon, (a) -> new LinkedList<>());
                menus.addAll(addonSubmenus.executionSubmenus);
            }
        }
    }

    private void registerLifecycle(NCommandSignsManager manager, Addon addon) {
        if (addon.shouldAddonBeHooked()) {
            final NCommandSignsAddonLifecycleHolder lifecycleHolder = manager.getLifecycleHolder();
            final AddonLifecycleHooker lifecycleHooker = addon.getLifecycleHooker();

            final Class<? extends AddonLifecycleHooker> hookerClass = lifecycleHooker.getClass();
            final Method[] declaredMethods = hookerClass.getDeclaredMethods();
            for (final Method method : declaredMethods) {
                if (method.isAnnotationPresent(NCSLifecycleHook.class)) {
                    final String name = method.getName();
                    switch (name) {
                        case "onStarted":
                            lifecycleHolder.onStartHandlers.add(addon);
                            break;
                        case "onRequirementCheck":
                            lifecycleHolder.onRequirementCheckHandlers.add(addon);
                            break;
                        case "onCostWithdraw":
                            lifecycleHolder.onCostWithdrawHandlers.add(addon);
                            break;
                        case "onPreExecution":
                            lifecycleHolder.onPreExecutionHandlers.add(addon);
                            break;
                        case "onPostExecution":
                            lifecycleHolder.onPostExecutionHandlers.add(addon);
                            break;
                        case "onCompleted":
                            lifecycleHolder.onCompletedHandlers.add(addon);
                            break;
                    }
                }
            }
        }
    }
}

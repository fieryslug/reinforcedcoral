package com.fieryslug.reinforcedcoral.minigame;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.Team;

import javax.swing.JPanel;

public interface PanelMiniGame {

    void react(Team team, ControlKey key);

    void applyTexture();

    void refreshRendering(boolean isFullScreen);

}

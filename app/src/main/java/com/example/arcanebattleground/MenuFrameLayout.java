package com.example.arcanebattleground;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

public class MenuFrameLayout extends FrameLayout {
    private LinearLayout containerLayout;
    private Context context;
    private MainMenu mainMenu;

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public MenuFrameLayout(@NonNull Context context) {
        super(context);
        this.addView(new MainMenu(context, this));

        LinearLayout containerLayout = new LinearLayout(context);
        containerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // Create and add the CenteredInputLayout to the container
        EnterGameIdView inputLayout = new EnterGameIdView(context, this);
        containerLayout.addView(inputLayout);
        containerLayout.setGravity(Gravity.CENTER);

        this.containerLayout = containerLayout;
        this.context = context;
    }
    public void displayInputLayout(){
        // Add the containerLayout with centered content to the FrameLayout
        this.addView(containerLayout);
    }
    public void displayLobbyView(){
        this.addView(new LobbyView(context));
    }
}

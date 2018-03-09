package com.example.micua.pocketbuddy;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SVBar;

import org.greenrobot.eventbus.EventBus;

public class ColorPickerDialog extends Dialog{

    private ColorPicker colorPicker;
    private SVBar svBar;
    private Button done;
    private boolean solid = false, start = false, end = false;

    public ColorPickerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_color_picker);

        colorPicker = findViewById(R.id.color_picker_wheel);
        svBar = findViewById(R.id.svbar);
        done = findViewById(R.id.btn_done_dialog);

        colorPicker.addSVBar(svBar);
        colorPicker.setShowOldCenterColor(false);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorEvent event = new ColorEvent();
                if (solid)
                    event.setSolidColor(colorPicker.getColor());
                if (start)
                    event.setStartColor(colorPicker.getColor());
                if (end)
                    event.setEndColor(colorPicker.getColor());
                EventBus.getDefault().post(event);
                dismiss();
            }
        });

    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getColor() {
        return colorPicker.getColor();
    }
}

package mx.ipn.escom.canon;

import android.app.ActionBar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import mx.ipn.escom.canon.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton disparar = (FloatingActionButton) view.findViewById(R.id.fuegoId);
        FloatingActionButton enviarAngulo = (FloatingActionButton) view.findViewById(R.id.enviarAnguloId);
        FloatingActionButton bluetoothButton = (FloatingActionButton) view.findViewById(R.id.bluetoothId);
        EditText velocidadInicial = (EditText) view.findViewById(R.id.velocidadInicialId);
        SeekBar angulo = (SeekBar) view.findViewById(R.id.anguloId);
        TextView textAngulo = (TextView) view.findViewById(R.id.textAnguloId);
        SeekBar distanciaX = (SeekBar) view.findViewById(R.id.distanciaXId);
        TextView textDistanciaX = (TextView) view.findViewById(R.id.textDistanciaXId);;
        TextView textMaxX = (TextView) view.findViewById(R.id.textMaxXId);
        SeekBar distanciaY = (SeekBar) view.findViewById(R.id.distanciaYId);
        TextView textDistanciaY = (TextView) view.findViewById(R.id.textDistanciaYId);
        TextView textMaxY = (TextView) view.findViewById(R.id.textMaxYId);
        MediaPlayer myMediaPlayer = MediaPlayer.create(getContext(), R.raw.fire);

        enviarAngulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float seekValue = angulo.getProgress();
                String x = Float.toString(seekValue) + "º";
                Snackbar.make(view, "Enviando: "+x, 1000*10).show();
                enviarAngulo.setEnabled(false);
                disparar.setEnabled(false);
                bluetoothButton.setEnabled(false);
                new CountDownTimer(10*1000,1000){
                    public void onFinish() {
                        enviarAngulo.setEnabled(true);
                        disparar.setEnabled(true);
                        bluetoothButton.setEnabled(true);
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();
                /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
            }
        });
        disparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float seekValue = angulo.getProgress();
                String x = Float.toString(seekValue) + "º";
                Snackbar.make(view, "¡Disparando!", 1000*3).show();
                enviarAngulo.setEnabled(false);
                disparar.setEnabled(false);
                bluetoothButton.setEnabled(false);
                myMediaPlayer.start();
                new CountDownTimer(3*1000,1000){
                    public void onFinish() {
                        enviarAngulo.setEnabled(true);
                        disparar.setEnabled(true);
                        bluetoothButton.setEnabled(true);
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();
                /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
            }
        });
        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float seekValue = angulo.getProgress();
                String x = Float.toString(seekValue) + "º";
                Snackbar.make(view, "Conectando...", 1000*3).show();
                enviarAngulo.setEnabled(false);
                disparar.setEnabled(false);
                bluetoothButton.setEnabled(false);
                new CountDownTimer(3*1000,1000){
                    public void onFinish() {
                        enviarAngulo.setEnabled(true);
                        disparar.setEnabled(true);
                        bluetoothButton.setEnabled(true);
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();
                /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
            }
        });

        velocidadInicial.addTextChangedListener(new TextWatcher() {
            private Editable editable;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*float a = Float.parseFloat(String.valueOf(s));
                Snackbar.make(view, String.valueOf(a), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                angulo.setProgress(0);
                angulo.setProgress(45);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                this.editable = editable;
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

        });
        angulo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                textAngulo.setText(String.valueOf((float) progress)+"°");

                /*
                Toast xd = Toast.makeText(getParentFragment().getContext(), a.length()+"a",Toast. LENGTH_SHORT);
                xd.setMargin(50,50);
                xd.show();*/

                double v0 = 0;
                if(velocidadInicial.getText().length()>1){
                    v0 = Double.parseDouble(String.valueOf(velocidadInicial.getText().toString()));
                }
                double grado = progress*Math.PI/180;

                double x = Math.sin(2*Math.PI/4)*v0*v0/9.81;
                x = Math.floor(x*100)/100;
                distanciaX.setMax((int) Math.round(x));
                textMaxX.setText(Double.toString(x));

                x = Math.sin(2*grado)*v0*v0/9.81;
                x = Math.floor(x*100)/100;
                distanciaX.setProgress((int) Math.round(x));
                textDistanciaX.setText(x+ " m");



                double y = Math.sin(Math.PI/2)*Math.sin(Math.PI/2)*v0*v0/(2*9.81);
                y = Math.floor(y*100)/100;
                distanciaY.setMax((int) Math.round(y));
                textMaxY.setText(Double.toString(y));

                y = Math.sin(grado)*Math.sin(grado)*v0*v0/(2*9.81);
                y = Math.floor(y*100)/100;
                distanciaY.setProgress((int) Math.round(y));
                textDistanciaY.setText(y+ " m");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        distanciaX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                textDistanciaX.setText(String.valueOf((float) progress)+" m");



            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double v0 = 0;
                if(velocidadInicial.getText().length()>1){
                    v0 = Double.parseDouble(String.valueOf(velocidadInicial.getText().toString()));
                }
                int progress = distanciaX.getProgress();
                double grado = (180/Math.PI)*Math.asin(progress*9.81/(v0*v0))/2;
                if(progress*9.81/(v0*v0) >= 1 || grado >= 45){
                    grado=45;
                }
                angulo.setProgress((int) Math.round(grado));
            }
        });
        distanciaY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                textDistanciaY.setText(String.valueOf((float) progress)+" m");


            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double v0 = 0;
                if(velocidadInicial.getText().length()>1){
                    v0 = Double.parseDouble(String.valueOf(velocidadInicial.getText().toString()));
                }
                int progress = distanciaY.getProgress();
                double sqrt = Math.sqrt(progress*2*9.81/(v0*v0));
                double grado = (180/Math.PI)*Math.asin(sqrt);
                if(sqrt >= 1 || grado >= 90){
                    grado=90;
                }
                angulo.setProgress((int) Math.floor(grado));
            }
        });
        velocidadInicial.setText(velocidadInicial.getText());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
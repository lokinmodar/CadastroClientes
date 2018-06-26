package br.com.dantesouzaesouza.cadastroclientes.data.model;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao;


public class SampleEventoDeValidacao implements EventoDeValidacao { //para validar CPF

    private final TextInputLayout textInputLayout;

    public SampleEventoDeValidacao(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void invalido(String valorAtual, String mensagem) {
        textInputLayout.setError(mensagem);
    }

    @Override
    public void parcialmenteValido(String valorAtual) {
        textInputLayout.setErrorEnabled(false);
        textInputLayout.setError(null);
    }

    @Override
    public void totalmenteValido(String valorAtual) {

        Toast.makeText(textInputLayout.getContext(), "CPF: "+ valorAtual + " Válido!", Toast.LENGTH_LONG).show();
        /*AlertDialog.Builder builder = new AlertDialog.Builder(textInputLayout.getContext())
                .setTitle("CPF Válido!")
                .setMessage(valorAtual);
        final AlertDialog dialog = builder.setNeutralButton("OK", (dialog1, which) -> {
        }).create();
        dialog.setOnShowListener(arg0 -> dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.BLACK));
        dialog.show();*/
    }
}
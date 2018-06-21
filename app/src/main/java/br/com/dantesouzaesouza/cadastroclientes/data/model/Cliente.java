package br.com.dantesouzaesouza.cadastroclientes.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Cliente implements Serializable, Parcelable {

   int codCliente;
   long cpf;
   int idade;
   long telefone;
   String cidade;


   protected Cliente(Parcel in) {
      codCliente = in.readInt();
      cpf = in.readLong();
      idade = in.readInt();
      telefone = in.readLong();
      cidade = in.readString();
   }

   public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
      @Override
      public Cliente createFromParcel(Parcel in) {
         return new Cliente(in);
      }

      @Override
      public Cliente[] newArray(int size) {
         return new Cliente[size];
      }
   };

   public int getCodCliente() {
      return codCliente;
   }

   public void setCodCliente(int codCliente) {
      this.codCliente = codCliente;
   }

   public long getCpf() {
      return cpf;
   }

   public void setCpf(long cpf) {
      this.cpf = cpf;
   }

   public int getIdade() {
      return idade;
   }

   public void setIdade(int idade) {
      this.idade = idade;
   }

   public long getTelefone() {
      return telefone;
   }

   public void setTelefone(long telefone) {
      this.telefone = telefone;
   }

   public String getCidade() {
      return cidade;
   }

   public void setCidade(String cidade) {
      this.cidade = cidade;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(codCliente);
      dest.writeLong(cpf);
      dest.writeInt(idade);
      dest.writeLong(telefone);
      dest.writeString(cidade);
   }

    @Override
    public String toString() {
        return "Cliente{" +
                "codCliente=" + codCliente +
                ", cpf=" + cpf +
                ", idade=" + idade +
                ", telefone=" + telefone +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}


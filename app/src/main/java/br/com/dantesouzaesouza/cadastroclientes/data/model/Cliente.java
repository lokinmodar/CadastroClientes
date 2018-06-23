package br.com.dantesouzaesouza.cadastroclientes.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Cliente implements Serializable, Parcelable {


   String nome;
   int codCliente;
   long cpf;
   int idade;
   long telefone;
   String cidade;
   long dataCadastro;



   protected Cliente(Parcel in) {
      nome = in.readString();
      codCliente = in.readInt();
      cpf = in.readLong();
      idade = in.readInt();
      telefone = in.readLong();
      cidade = in.readString();
      dataCadastro = in.readLong();
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

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

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

   public long getDataCadastro() {
      return dataCadastro;
   }

   public void setDataCadastro(long dataCadastro) {
      this.dataCadastro = dataCadastro;
   }

   public static Creator<Cliente> getCREATOR() {
      return CREATOR;
   }


   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(nome);
      dest.writeInt(codCliente);
      dest.writeLong(cpf);
      dest.writeInt(idade);
      dest.writeLong(telefone);
      dest.writeString(cidade);
      dest.writeLong(dataCadastro);
   }

   @Override
   public String toString() {
      return "Cliente{" +
              "nome='" + nome + '\'' +
              ", codCliente=" + codCliente +
              ", cpf=" + cpf +
              ", idade=" + idade +
              ", telefone=" + telefone +
              ", cidade='" + cidade + '\'' +
              ", dataCadastro=" + dataCadastro +
              '}';
   }
}


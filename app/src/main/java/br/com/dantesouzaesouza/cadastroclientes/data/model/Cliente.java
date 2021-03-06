package br.com.dantesouzaesouza.cadastroclientes.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Cliente implements Parcelable { //Classe de apoio para dados de Clientes

   int codigo;
   String nome;
   long cpf;
   int idade;
   long telefone;
   String cidade;
   long dataCadastro;

   protected Cliente(Parcel in) {
      codigo = in.readInt();
      nome = in.readString();
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

   public Cliente(int codigo, String nome, long cpf, int idade, long telefone, String cidade, long dataCadastro) {
      this.codigo = codigo;
      this.nome = nome;
      this.cpf = cpf;
      this.idade = idade;
      this.telefone = telefone;
      this.cidade = cidade;
      this.dataCadastro = dataCadastro;
   }

   public int getCodigo() {
      return codigo;
   }

   public void setCodigo(int codigo) {
      this.codigo = codigo;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
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




   @Override
   public String toString() {
      return "Cliente{" +
              "codigo='" + codigo+ '\'' +
              ", nome='" + nome + '\'' +
              ", cpf=" + cpf +
              ", idade=" + idade +
              ", telefone=" + telefone +
              ", cidade='" + cidade + '\'' +
              ", dataCadastro=" + dataCadastro +
              '}';
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(codigo);
      dest.writeString(nome);
      dest.writeLong(cpf);
      dest.writeInt(idade);
      dest.writeLong(telefone);
      dest.writeString(cidade);
      dest.writeLong(dataCadastro);
   }
}


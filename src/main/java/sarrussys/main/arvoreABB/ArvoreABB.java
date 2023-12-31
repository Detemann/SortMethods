package sarrussys.main.arvoreABB;

import sarrussys.main.models.Item;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import static java.lang.Long.parseLong;

public class ArvoreABB {
    private NoABB raiz;
    private int quant;

    public ArvoreABB() {
        this.quant = 0;
        this.raiz = null;
    }

    /*
        METODO DE INSERIR ORIGINAL
     */
    public boolean inserir (Item item) {
        NoABB aux = pesquisar (item.getChave());
        if (aux != null) {
            return false;
        } else {
            this.raiz = inserir (item, this.raiz);
            return true;
        }
    }
    private NoABB inserir (Item novoItem, NoABB no) {
        if (no==null) {
            no = new NoABB(novoItem);
            this.quant++;
        } else if (novoItem.getChave().compareTo(no.getItem().getChave()) > 0) {
            no.setDir(inserir (novoItem, no.getDir()));
        } else {
            no.setEsq(inserir (novoItem, no.getEsq()));
        }
        return no;
    }

    /*
     METODO DE PESQUISA ORIGINAL
     */
    public NoABB pesquisar (String chave) {
        return pesquisar (chave, this.raiz);
    }
    private NoABB pesquisar (String chave, NoABB no) {
        if (no == null) {
            return null;
        } else if (Objects.equals(chave, no.getItem().getChave())) {
            return no;
        } else if (chave.compareTo(no.getItem().getChave()) > 0) {
            return pesquisar(chave, no.getDir());
        } else {
            return pesquisar(chave, no.getEsq());
        }
    }

    /*
        BALANCEAMENTO ARVORE ABB
     */
    //CAMINHAMENTO CENTRAL
    public VetorItem CamCentral () {
        VetorItem vetor = new VetorItem(quant);
        return (fazCamCentral(this.raiz, vetor));
    }

    private VetorItem fazCamCentral (NoABB no, VetorItem vetor) {
        if (no != null) {
            vetor = this.fazCamCentral(no.getEsq(), vetor);
            vetor.inserir(no.getItem());
            vetor = this.fazCamCentral(no.getDir(), vetor);
        }
        return vetor;
    }

    //BALANCEAMENTO
    public ArvoreABB balancear () {
        ArvoreABB arv = new ArvoreABB();
        VetorItem vetor = CamCentral();
        balancear (vetor, arv, 0, quant-1);
        return arv;
    }
    private void balancear (VetorItem vetor, ArvoreABB arv, int inicio, int fim) {
        int meio;
        if (inicio <= fim) {
            meio = (inicio+fim)/2;
            arv.inserir(vetor.get(meio));
            // balancear a parte esquerda do vetor
            balancear (vetor, arv, inicio, meio-1);
            // balancear a parte direita do vetor
            balancear(vetor, arv, meio+1, fim);
        }
    }

}

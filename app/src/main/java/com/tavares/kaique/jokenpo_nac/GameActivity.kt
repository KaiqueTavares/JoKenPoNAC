package com.tavares.kaique.jokenpo_nac

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : AppCompatActivity() {

    //Criando uma variavel que ela atribui um numero random que pode ser nulo
    private var numeroAleatorio:Random?=null

    //VARIAVEIS DE CONTROLE DE ESCOLHA DE JOGADA
    private var PEDRA = 1
    private var PAPEL = 2
    private var TESOURA = 3

    //Funções de Derrota Vitoria e empate
    private fun venceu(){
        //Pego meu TextView Resultado, atribuio um texto onde vou pegar uma string do meu arquivo:
        //values -> strings.xml
        tvResultado!!.text = getString(R.string.venceu)
        //Atribuindo uma cor ao meu texto, onde pego no XML color.
        tvResultado!!.setTextColor(ContextCompat.getColor(this,R.color.vitoria))
    }

    private fun perdeu(){
        tvResultado!!.text = getString(R.string.perdeu)
        tvResultado!!.setTextColor(ContextCompat.getColor(this,R.color.derrota))
    }

    private fun empate(){
        tvResultado!!.text = getString(R.string.empate)
        tvResultado!!.setTextColor(ContextCompat.getColor(this,R.color.empate))
    }

    //Criando um metodo que sera responsavel por dar as jogadas ao pc e tudo mais
    private fun realizarJogada(jogadaPlayer:Int){
        //Ao chamar minha função eu crio uma variavel que recebe um Media Player (Som), com o audio que escolhi
        var player = MediaPlayer.create(this,R.raw.jokenpo)
        //Pego a variavel e faço ela tocar.
        player.start()

        //A jogada PC vai ser uma variavel que recebe o numero aleatorio e que atribui mais 1 pois nossos privates
        //Privates INTs sao 1 2 e 3, mas como falamos que ele vai dar um random em 3 ele retorna (0,1,2)
        var jogadaPC = numeroAleatorio!!.nextInt(3)+1

        //Quando eu tiver qualquer numero do Jogada PC
        when (jogadaPC){
            //Se for pedra, vale lembrar que ele terá retornado 1
            PEDRA ->{
                //Troco minha iamgem para a pedra
                ivResultadoPC!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pedra))
                //Qunado eu receber o parametro que eu passar quando chamar a func
                when (jogadaPlayer){
                    //Se este parametro for papel ele vence, por que papel embrulha a pedra.
                    PAPEL -> venceu()
                    PEDRA -> empate()
                    TESOURA -> perdeu()
                }
            }

            PAPEL -> {
                ivResultadoPC!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.papel))
                when(jogadaPlayer){
                    PAPEL -> empate()
                    PEDRA -> perdeu()
                    TESOURA -> venceu()
                }
            }

            TESOURA -> {
                ivResultadoPC!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.tesoura))
                when(jogadaPlayer){
                    PAPEL -> perdeu()
                    PEDRA -> venceu()
                    TESOURA -> empate()
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //Minha variavel recebe um valor random
        numeroAleatorio=Random()

        //Quando o usuario clicar na pedra
        ivPedra.setOnClickListener {
            //Ele irá trocar a imagem para pedra
            ivResultadoPlayer!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pedra))
            //Vou chamar minha função passando o valor de PEDRA para que a função calcule e nos de o resultado.
            realizarJogada(PEDRA)
        }

        ivPapel.setOnClickListener {
            ivResultadoPlayer!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.papel))
            realizarJogada(PAPEL)
        }

        ivTesoura.setOnClickListener {
            ivResultadoPlayer!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.tesoura))
            realizarJogada(TESOURA)
        }

    }
}

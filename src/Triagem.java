import eduni.simjava.*;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

/**
 * Created by faria on 31/10/2018.
 */
public class Triagem extends Sim_entity {
    private Sim_port balcaoDeAtendimento, clinicoGeral, otorrino, cardio;
    private Sim_normal_obj delay;
    private Sim_random_obj prob;

    //Objeto para as medidas de estatisticas
    private Sim_stat stat;

    Triagem (String nome, double media, double variancia) {

        super(nome);

        //Criando porta de entrada
        balcaoDeAtendimento = new Sim_port("BalcaoDeAtendimento");

        //Cria demais portas
        clinicoGeral = new Sim_port("ClinicoGeral");
        otorrino = new Sim_port("Otorrinolaringologista");
        cardio = new Sim_port("Cardiologista");

        //Adicionando portas para Evento
        add_port(balcaoDeAtendimento);
        add_port(clinicoGeral);
        add_port(otorrino);
        add_port(cardio);

        //Gerando distribuilcao de probabilidade
        delay = new Sim_normal_obj("Delay", media, variancia);
        prob = new Sim_random_obj("Probability");
        add_generator(delay);
        add_generator(prob);

        //Medidas de estatistica
        stat = new Sim_stat();

        stat.add_measure(Sim_stat.ARRIVAL_RATE); //Taxa de chegada
        stat.add_measure(Sim_stat.QUEUE_LENGTH); //Tamanho da fila
        stat.add_measure(Sim_stat.WAITING_TIME); //Tempo de espera
        stat.add_measure(Sim_stat.UTILISATION);  //Utilização
        stat.add_measure(Sim_stat.RESIDENCE_TIME); //Tempo de resposta

        set_stat(stat);

    }

    public void body() {

        while (Sim_system.running()) {

            //Cria o evento
            Sim_event e = new Sim_event();

            //Pega o proximo evento
            sim_get_next(e);

            //Processa o evento com a amostra da distribuicao
            sim_process(delay.sample());

            //Completa a execução do evento
            sim_completed(e);

            double p = prob.sample();

            if (p <= 0.50) {
                //50% vai para o clinico geral
                sim_trace(1, "Pessoa vai para o clinico geral");
                sim_schedule(clinicoGeral, 0.0, 1);

            } else if (p <= 0.80) {
                //30% vai para o otorrinolaringologista
                sim_trace(1, "Pessoa vai para o otorrino");
                sim_schedule(otorrino, 0.0, 1);

            } else {
                //20% vai para o cardiologista
                sim_trace(1, "Pessoa vai para o cardio");
                sim_schedule(cardio, 0.0, 1);
            }

        }
    }
}

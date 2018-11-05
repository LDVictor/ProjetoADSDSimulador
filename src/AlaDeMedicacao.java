import eduni.simjava.*;
import eduni.simjava.distributions.Sim_negexp_obj;

/**
 * Created by faria on 31/10/2018.
 */
public class AlaDeMedicacao extends Sim_entity {
    private Sim_port postoDeEnfermagem;
    private Sim_negexp_obj delay;
    private Sim_stat stat;

    AlaDeMedicacao (String nome, double media) {

        super(nome);

        postoDeEnfermagem = new Sim_port("PostoDeEnfermagem");
        add_port(postoDeEnfermagem);

        delay = new Sim_negexp_obj("Delay", media);
        add_generator(delay);

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

            Sim_event e = new Sim_event();

            sim_get_next(e);

            sim_process(delay.sample());
            //sim_process(delay);

            sim_completed(e);

            sim_trace(1, "Pessoa vai embora do hospital");
        }
    }
}

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

/**
 * Created by faria on 31/10/2018.
 */
public class PostoDeEnfermagem extends Sim_entity {
  private Sim_port clinicoGeral, otorrino, cardio, alaDeMedicacao;
  private Sim_normal_obj delay;
  private Sim_random_obj prob;


  private Sim_stat stat;

  PostoDeEnfermagem(String nome, double media, double variancia) {

    super(nome);

    clinicoGeral = new Sim_port("ClinicoGeral");
    otorrino = new Sim_port("Otorrinolaringologista");
    cardio = new Sim_port("Cardiologista");
    alaDeMedicacao = new Sim_port("AlaDeMedicacao");


    add_port(clinicoGeral);
    add_port(otorrino);
    add_port(cardio);
    add_port(alaDeMedicacao);

    delay = new Sim_normal_obj("Delay", media, variancia);
    prob = new Sim_random_obj("Probability");

    add_generator(delay);
    add_generator(prob);

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

      sim_completed(e);

      double p = prob.sample();

      if (p <= 0.50) {
        //50% vai para a ala de medicação
        sim_schedule(alaDeMedicacao, 0.0, 1);
      } else {
        //50% vai embora do hospital
        sim_trace(1, "Pessoa vai embora do hospital");
      }
    }
  }
}

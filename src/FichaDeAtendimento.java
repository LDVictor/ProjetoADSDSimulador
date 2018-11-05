import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.distributions.Sim_negexp_obj;

/**
 * Created by faria on 31/10/2018.
 */
public class FichaDeAtendimento extends Sim_entity {

    private Sim_port balcaoDeAtendimento;
    private Sim_negexp_obj delay;

    FichaDeAtendimento (String nome, double media) {

        super(nome);

        balcaoDeAtendimento = new Sim_port("BalcaoDeAtendimento");
        add_port(balcaoDeAtendimento);

        delay = new Sim_negexp_obj("Delay", media);
        add_generator(delay);

    }

    public void body() {

        for (int i = 0; i < 100; i++) {

            sim_schedule(balcaoDeAtendimento, 0.0, 1);

            sim_trace(1, "Nova pessoa pegou ficha no sistema\n");

            sim_pause(delay.sample());
        }
    }

}

package com.example.sergio.pruebaslide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String url="http://181.14.240.59/Portal/wp-content/uploads/2017/08/Imagen11.jpeg";
    String[] descripcion[];
    private String url2="http://181.14.240.59/Portal/wp-content/uploads/2017/08/WhatsApp-Image-2017-08-08-at-09.45.20-500x290.jpeg";
    private ImageView img1, img2, img3, img4;
    private TextView tvd1,tvd2,tvd3,tvd4 ;
    private Element tomarDiv2, tomarDiv;
    private Elements parrafos, parrafos2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView)findViewById(R.id.imageView);
        img2 = (ImageView)findViewById(R.id.imageView);
//        img1 = (ImageView)findViewById(R.id.imageView);
//        img1 = (ImageView)findViewById(R.id.imageView);

        tvd1 = (TextView)findViewById(R.id.tvDescripcion);
        tvd2 = (TextView)findViewById(R.id.tvDescripcion);
//        tvd3 = (TextView)findViewById(R.id.tvDescripcion);
//        tvd4 = (TextView)findViewById(R.id.tvDescripcion);


        cargarImagenes();
        cargarDescripcion();


    }


    public void  cargarImagenes(){
        // carga la direccion de la imagen en url y lo añade a img
        // cargamos las imagenes con picasso
        Picasso.with(getApplication()).
                load(url).into(img1);
        Picasso.with(getApplication()).
                load(url2).into(img2);


    }

    public void cargarDescripcion(){
        //Crear un nuevo hilo, ya que el hilo principal no permite que se realicen tareas largas o con acceso a internet.
        new Thread(new Runnable() {
            public void run() {
                //En esta sección realizar todo el trabajo pesado, ya que es el comienzo de un nuevo hilo creado
                Document doc, doc2;
                try {
                    //necesitará protocolo http
                    //doc trae el html completo de la url que se le agregue
                    doc = Jsoup.connect("http://181.14.240.59/Portal/entrenamiento-laboral-en-el-sheraton/")
                            .userAgent("Mozilla")
                            .get();
                    //La función title() lo que hace es buscar el atributo <title> y lo trae en forma de string.
                    tomarDiv = doc.select("div.postview_content").first();// se toma el dic con el punto accesor(por que es una clase) y para id se usa #
                    parrafos =  tomarDiv.getElementsByTag("p");
                    descripcion[0] = parrafos.get(1).text();


                    //Utilizar Element para buscar un elemento en especial, en este caso el id hplogo que se encuentra dentro de un <div>.
                    //Element image = doc.select("div[id=hplogo]").first();
                    //Buscamos el atributo style y lo convertimos a string, el atributo style utiliza el Element image
                    //titleid=image.attr("style").toString();

                    // la 4 lineas de codigo comentadas arriba no son usadas aqui

                    // un nuevo
                    doc2 = Jsoup.connect("http://181.14.240.59/Portal/cierre-de-los-cursos-de-introduccion-al-trabajo/").
                            userAgent("Mozilla").
                            get();
                    tomarDiv2 = doc2.select("div.postview_content").first();
                    parrafos2 = tomarDiv2.getElementsByTag("p");
                    descripcion[1] = parrafos2.get(1).text();








                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Mostrar los resultados.
                runOnUiThread(new Runnable() {
                    public void run() {
                        // ahora seteamos
                        tvd1.setText(descripcion);
                        tvd2.setText(descripcion2);
                        //Como ya se vincularon los componentes, se podrá utilizar sin problemas.
                        //En esta parte siempre mostrar los resultados.
                         Toast.makeText(MainActivity.this, "Carga Completa", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).start();



    }
}


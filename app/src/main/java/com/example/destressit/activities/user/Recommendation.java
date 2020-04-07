package com.example.destressit.activities.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.destressit.ImageAdapter;
import com.example.destressit.R;
import com.example.destressit.core.PreferenceUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

public class Recommendation extends AppCompatActivity {


    private Object ViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        TextView TV1 = (TextView) findViewById(R.id.tv1);
        TextView TV2 = (TextView) findViewById(R.id.tv2);
        TextView TV3 = (TextView) findViewById(R.id.tv3);
        TextView TV4 = (TextView) findViewById(R.id.tv4);
        TextView TV5 = (TextView) findViewById(R.id.tv5);
        TextView TV6 = (TextView) findViewById(R.id.tv6);
        TextView TV7 = (TextView) findViewById(R.id.tv7);
        TextView TV8 = (TextView) findViewById(R.id.tv8);
        TextView TV9 = (TextView) findViewById(R.id.tv9);
        TextView TV10 = (TextView) findViewById(R.id.tv10);
        TextView TV11 = (TextView) findViewById(R.id.tv11);
        TextView TV12 = (TextView) findViewById(R.id.tv12);
        TextView TV13 = (TextView) findViewById(R.id.tv13);
        TextView TV14 = (TextView) findViewById(R.id.tv14);
        TextView TV15 = (TextView) findViewById(R.id.tv15);
        TextView TV16 = (TextView) findViewById(R.id.tv16);
        TextView TV17 = (TextView) findViewById(R.id.tv17);
        TextView TV18 = (TextView) findViewById(R.id.tv18);
        TextView TV19 = (TextView) findViewById(R.id.tv19);
        TextView TV20 = (TextView) findViewById(R.id.tv20);
        TextView TV21 = (TextView) findViewById(R.id.tv21);
        TextView TV22 = (TextView) findViewById(R.id.tv22);
        TextView TV23 = (TextView) findViewById(R.id.tv23);
        TextView TV24 = (TextView) findViewById(R.id.tv24);
        TextView TV25 = (TextView) findViewById(R.id.tv25);
        TextView TV26 = (TextView) findViewById(R.id.tv26);


        ViewPager mViewPager = (ViewPager)findViewById(R.id.ViewPager1);
        ImageAdapter adapterView = new ImageAdapter(this);
        mViewPager.setAdapter(adapterView);

        Long stressLevel = PreferenceUtil.getLong(getApplicationContext(),"stressLevel",0);


        if(stressLevel <= 35) {
            TV1.setText("Deep Breathing");
            TV2.setText("Deep Breathing can immediately help you to relaxand reduce productionof stgress hormones. In the long run, it can improve focus, brain functioning, and benefit the body's digestion and immune system");
            TV3.setText("Guided Imagery");
            TV4.setText("Guided Imagery is a good way to wind down and calm your body after a stressful event. Research shows that it positively impacts heart-rate, blood pressure and breathing.");
            TV9.setText("Meditation");
            TV10.setText("Meditation is a process in which we shift from thinking to feeling. It is a journey from the complexity of mind to the simplicity of heart. Various activities are included in medtitation centers where one can visit to relax the mind, body and soul.");

            CardView cardView3 = findViewById(R.id.card_view3);
            cardView3.setVisibility(View.GONE);
            CardView cardView4 = findViewById(R.id.card_view4);
            cardView4.setVisibility(View.GONE);
            LinearLayout physical = findViewById(R.id.LL1);
            physical.setVisibility(View.GONE);
        }
        else if (stressLevel > 35 && stressLevel < 70){
            TV1.setText("Deep Breathing");
            TV2.setText("Deep Breathing can immediately help you to relaxand reduce productionof stgress hormones. In the long run, it can improve focus, brain functioning, and benefit the body's digestion and immune system");
            TV3.setText("Guided Imagery");
            TV4.setText("Guided Imagery is a good way to wind down and calm your body after a stressful event. Research shows that it positively impacts heart-rate, blood pressure and breathing.");
            TV5.setText("Self-Compassion");
            TV6.setText("You might find it more difficult to be kind to yourself as compared to others around you. Through self-compassion, you can feel more positive and even cope better in difficult situations");
            TV9.setText("Meditation");
            TV10.setText("Meditation is a process in which we shift from thinking to feeling. It is a journey from the complexity of mind to the simplicity of heart. Various activities are included in medtitation centers where one can visit to relax the mind, body and soul.");

            TV11.setText("Walking");
            TV12.setText("Walk in nearby park or garden along with a colleague");
            TV13.setText("Walking is one of the most convenient ways to reduce stress as it triggers the release of endorphins that make you feel relaxed and even relieve pain. These endorphins induce a sense of calm and well-being");
            TV23.setText("Yoga Nidra");
            TV24.setText("Yoga nidra is a state in which the body is completely relaxed, and the practitioner becomes systematically and increasingly aware of the inner world by following a set of verbal instructions.");
            TV25.setText("Outdoor Games");
            TV26.setText("There’s something rejuvenating about the great outdoors that you would love to do, so if you’re feeling stressed, you might as well get out there. With vitamin D, fresh air and nature’s beauty for you to take in, you’ll be waving goodbye to your worries in no time at all.");


            CardView cardView4 = findViewById(R.id.card_view4);
            cardView4.setVisibility(View.GONE);
            CardView cardView7 = findViewById(R.id.card_view7);
            cardView7.setVisibility(View.GONE);
            CardView cardView8 = findViewById(R.id.card_view8);
            cardView8.setVisibility(View.GONE);

        }
        else if(stressLevel >= 70){

            TV1.setText("Deep Breathing");
            TV2.setText("Deep Breathing can immediately help you to relaxand reduce productionof stgress hormones. In the long run, it can improve focus, brain functioning, and benefit the body's digestion and immune system");
            TV3.setText("Guided Imagery");
            TV4.setText("Guided Imagery is a good way to wind down and calm your body after a stressful event. Research shows that it positively impacts heart-rate, blood pressure and breathing.");
            TV5.setText("Self-Compassion");
            TV6.setText("You might find it more difficult to be kind to yourself as compared to others around you. Through self-compassion, you can feel more positive and even cope better in difficult situations");
            TV7.setText("Thought Defusion");
            TV8.setText("n difficult situations, you might get negative thoughts that play on your mind. Using thought defusion, you can become aware of these thoughts. You can then let go of them or change them into positive ones.");
            TV9.setText("Meditation");
            TV10.setText("Meditation is a process in which we shift from thinking to feeling. It is a journey from the complexity of mind to the simplicity of heart. Various activities are included in medtitation centers where one can visit to relax the mind, body and soul.");

            TV11.setText("Walking");
            TV12.setText("Walk in nearby park or garden along with a colleague");
            TV13.setText("Walking is one of the most convenient ways to reduce stress as it triggers the release of endorphins that make you feel relaxed and even relieve pain. These endorphins induce a sense of calm and well-being");
            TV14.setText("Cycling");
            TV15.setText("Try cycling in an open area with a colleague - it's fun to have some company");
            TV16.setText("Research shows that cycling reduces symptoms of stress and leaves you feeling more calm and focused.");
            TV17.setText("Skipping Ropes");
            TV18.setText("Try skipping with music when you are feeling stressed");
            TV19.setText("Skipping reduces stress by releasing endorphins which are not only pain relievers, but also create a sense of clam and well-being");
            TV20.setText("Climbing Stairs");
            TV21.setText("When stressed, take a short break and climb up and down four flights of stairs to relax");
            TV22.setText("Climbing the stairs is a physical activity that can be carried out easily. When you climb up and down, your body releases feel good hormones which are naturals pain relievers, and can greatly reduce feeling of stress");
            TV23.setText("Yoga Nidra");
            TV24.setText("Yoga nidra is a state in which the body is completely relaxed, and the practitioner becomes systematically and increasingly aware of the inner world by following a set of verbal instructions.");
            TV25.setText("Outdoor Games");
            TV26.setText("There’s something rejuvenating about the great outdoors that you would love to do, so if you’re feeling stressed, you might as well get out there. With vitamin D, fresh air and nature’s beauty for you to take in, you’ll be waving goodbye to your worries in no time at all.");

        }

    }

}
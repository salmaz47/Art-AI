package com.salmee.artai.data



import android.content.Context
import com.salmee.artai.R
import com.salmee.artai.model.PaintItem
import com.salmee.artai.model.Habit

object MockHabitsData {
    fun getMorningHabits(context: Context): List<Habit> {
        return listOf(
            Habit(
                1,
                context.getString(R.string.name_1),
                context.getString(R.string.descrip_1),
                R.drawable.mh_make_bed),
            Habit(
                2,
                context.getString(R.string.name_2),
                context.getString(R.string.descrip_2),
                R.drawable.mh_study),
            Habit
                (
                3,
                context.getString(R.string.name_3),
                context.getString(R.string.descrip_3),
                R.drawable.mh_brush_teeth),
            Habit(
                4,
                context.getString(R.string.name_4),
                context.getString(R.string.descrip_4),
                R.drawable.mh_take_path),
            Habit(
                5,
                context.getString(R.string.name_5),
                context.getString(R.string.descrip_5),
                R.drawable.mh_breakfast),
            Habit(
                6,
                context.getString(R.string.name_6),
                context.getString(R.string.descrip_6),
                R.drawable.mh_change_clothes),

        )
    }
    fun getNightHabits(context: Context): List<Habit> {
        return listOf(
            Habit(
                7,
                context.getString(R.string.name_12),
                context.getString(R.string.descrip_12),
                R.drawable.nh_dinner),
            Habit(
                8,
                context.getString(R.string.name_13),
                context.getString(R.string.descrip_13),
                R.drawable.nh_brush_teeth),
            Habit(
                  9
                ,
                context.getString(R.string.name_14),
                context.getString(R.string.descrip_14),
                R.drawable.nh_take_shower),
            Habit(
                10,
                context.getString(R.string.name_15),
                context.getString(R.string.descrip_15),
                R.drawable.nh_change_clothes)
        )
    }
    fun getAroundmeHabits(context: Context): List<Habit> {
        return listOf(
            Habit(
                7,
                context.getString(R.string.name_21),
                context.getString(R.string.descrip_21),
                R.drawable.ah_lundary),
            Habit(
                8,
                context.getString(R.string.name_22),
                context.getString(R.string.descrip_22),
                R.drawable.ah_washing_dishes),
            Habit(
                9,
                context.getString(R.string.name_23),
                context.getString(R.string.descrip_23),
                R.drawable.ah_collect_toys),
            Habit(
                11,
                context.getString(R.string.name_24),
                context.getString(R.string.descrip_24),
                R.drawable.ah_help_mom_incleaning),
            Habit(
                12,
                context.getString(R.string.name_25),
                context.getString(R.string.descrip_25),
                R.drawable.ah_cooking_with_mpmom),
            Habit(
                13,
                context.getString(R.string.name_26),
                context.getString(R.string.descrip_26),
                R.drawable.ah_organizing_closet),
        )
    }
    fun getSportsTeam(context: Context): List<Habit> {
        return listOf(

            Habit(
                10,
                context.getString(R.string.name_31),
                context.getString(R.string.descrip_31),
                R.drawable.sport7),
            Habit(
                12,
                context.getString(R.string.basketball),
                context.getString(R.string.basket_description) ,
                R.drawable.sport6),
            Habit(
                10,
                context.getString(R.string.volleyball),
                context.getString(R.string.volley_description) ,
                R.drawable.sport4),
            Habit(
                9,
                context.getString(R.string.handball),
                context.getString(R.string.hand_descriotion) ,
                R.drawable.sport3),


        )
    }
    fun getSportsIndividual(context: Context): List<Habit> {
        return listOf(
            Habit(
                11,
                context.getString(R.string.swimming),
                context.getString(R.string.swimming_description) ,
                R.drawable.sport5),
            Habit(
                8,
                context.getString(R.string.tennis),
                context.getString(R.string.tennis_description) ,
                R.drawable.sport2),
            Habit(
                7,
                context.getString(R.string.badminton),
                context.getString(R.string.badminton_description),
                R.drawable.sport1),
            )
    }
    fun getSportsMaterialArts(context: Context): List<Habit> {
        return listOf(
            Habit(
                11,
                context.getString(R.string.karate),
                context.getString(R.string.karate_description),
                R.drawable.sport_material_art1),
            Habit(8,
                context.getString(R.string.taekwondo),
                context.getString(R.string.taekwondo_description),
                R.drawable.sport_material_art2),
            Habit(7,
                context.getString(R.string.judo),
                context.getString(R.string.judo_description),
                R.drawable.sport_material_art3),
            Habit(7,
                context.getString(R.string.boxing),
                context.getString(R.string.boxing_description),
                R.drawable.sport_material_art4),
        )
    }
    fun getSportsArtistic(context: Context): List<Habit> {
        return listOf(
            Habit(
                11,
                context.getString(R.string.gymnastics),
                context.getString(R.string.gymnastics_description),
                R.drawable.sport_artistic1),
            Habit(11,
                context.getString(R.string.ballet),
                context.getString(R.string.ballet_description),
                R.drawable.sport_artistic2),

        )
    }
    fun getStory(context: Context): List<Habit> {
        return listOf(
            Habit(
                21,
                context.getString(R.string.story_sengab_name),
                context.getString(R.string.story_sengab_description) ,
                R.drawable.story_sengab),
            Habit(
                22,
                context.getString(R.string.story_lion_name),
                context.getString(R.string.story_lion_descrip),
                R.drawable.story_lion),
            Habit(
                23,
                context.getString(R.string.the_magic_paintbrush_name),
                context.getString(R.string.magic_paintbrush_descrip),  R.drawable.story_brush),
            Habit(
                24,
                context.getString(R.string.story_giraffe_name),
                context.getString(R.string.story_giraffe_descrip),
                R.drawable.story_giraff),
            Habit(
                25,
                context.getString(R.string.story_bee_name),
                context.getString(R.string.story_bee_descrip),
                R.drawable.story_bee),
            Habit(
                26,
                context.getString(R.string.story_ant_name),
                context.getString(R.string.story_ant_descrip),
                R.drawable.story_ant),
        )
    }
    fun getLetters(context: Context): List<Habit> {
        return listOf(
            Habit(11, context.getString(R.string.apple),"Get Dressed",  R.drawable.a),
            Habit(11, context.getString(R.string.blueberry),"Get Dressed",  R.drawable.b),
            Habit(11, context.getString(R.string.coconut),"Get Dressed",  R.drawable.c),
            Habit(11, context.getString(R.string.dragonfruit),"Get Dressed",  R.drawable.d),
            Habit(11, context.getString(R.string.elderberry),"Get Dressed",  R.drawable.e),
            Habit(11, context.getString(R.string.fig),"Get Dressed",  R.drawable.f),
            Habit(11, context.getString(R.string.grapefruit),"Get Dressed",  R.drawable.g),
            Habit(11, context.getString(R.string.hazelnut),"Get Dressed",  R.drawable.h),
            Habit(11, context.getString(R.string.ita_palm),"Get Dressed",  R.drawable.i),
            Habit(11, context.getString(R.string.jackfruit),"Get Dressed",  R.drawable.j),
            Habit(11, context.getString(R.string.kiwi),"Get Dressed",  R.drawable.k),
            Habit(11, context.getString(R.string.lemon),"Get Dressed",  R.drawable.l),
            Habit(11, context.getString(R.string.mango),"Get Dressed",  R.drawable.m),
            Habit(11, context.getString(R.string.nectarine),"Get Dressed",  R.drawable.n),
            Habit(11, context.getString(R.string.orange),"Get Dressed",  R.drawable.o),
            Habit(11, context.getString(R.string.pineapple),"Get Dressed",  R.drawable.p),
            Habit(11, context.getString(R.string.quince),"Get Dressed",  R.drawable.q),
            Habit(11, context.getString(R.string.raspberry),"Get Dressed",  R.drawable.r),
            Habit(11, context.getString(R.string.strawberry),"Get Dressed",  R.drawable.s),
            Habit(11, context.getString(R.string.tomato),"Get Dressed",  R.drawable.t),
            Habit(11, context.getString(R.string.ugli_fruit),"Get Dressed",  R.drawable.u),
            Habit(11, context.getString(R.string.to),"Get Dressed",  R.drawable.v),
            Habit(11, context.getString(R.string.watermelon),"Get Dressed",  R.drawable.w),
            Habit(11, context.getString(R.string.ximenia),"Get Dressed",  R.drawable.x),
            Habit(11, context.getString(R.string.yali_pear),"Get Dressed",  R.drawable.y),
            Habit(11, context.getString(R.string.vanilla_fruit),"Get Dressed",  R.drawable.z),
            )
    }

    fun getNumbers(context: Context): List<Habit> {
        return listOf(

            Habit(10, context.getString(R.string.one),"Get Dressed",  R.drawable.one),
            Habit(10, context.getString(R.string.two),"Get Dressed",  R.drawable.tw0),
            Habit(10, context.getString(R.string.three),"Get Dressed",  R.drawable.three),
            Habit(10, context.getString(R.string.four),"Get Dressed",  R.drawable.four),
            Habit(10, context.getString(R.string.five),"Get Dressed",  R.drawable.five),
            Habit(10, context.getString(R.string.six),"Get Dressed",  R.drawable.six),
            Habit(10, context.getString(R.string.seven),"Get Dressed",  R.drawable.seven),
            Habit(10, context.getString(R.string.eight),"Get Dressed",  R.drawable.eight),
            Habit(10, context.getString(R.string.nine),"Get Dressed",  R.drawable.nine),
            Habit(10, context.getString(R.string.ten),"Get Dressed",  R.drawable.ten),



            )
    }
    fun getShapes(context: Context): List<Habit> {
        return listOf(
            Habit(11, context.getString(R.string.circle),"Get Dressed",  R.drawable.circle),
            Habit(11, context.getString(R.string.triangle),"Get Dressed",  R.drawable.triangle),
            Habit(11, context.getString(R.string.square),"Get Dressed",  R.drawable.square),
            Habit(11, context.getString(R.string.rectangle),"Get Dressed",  R.drawable.rectangle),
            Habit(11, context.getString(R.string.star),"Get Dressed",  R.drawable.star),
            Habit(11, context.getString(R.string.rhombus),"Get Dressed",  R.drawable.rhombus),


            )
    }
    fun getDrawings(): List<PaintItem>
    {
        return listOf(
            PaintItem(R.drawable.puppy,"https://youtu.be/g55jWHQdq0c?si=EEiZibJwv-JUaD6g"),
            PaintItem(R.drawable.cat,"https://youtu.be/8GPOPkh1rLc?si=DxJfL8QJNV4m47j3"),
            PaintItem(R.drawable.lion,"https://youtu.be/wCfm-SH-rww?si=JFco-aDZQLY_5y3e"),
            PaintItem(R.drawable.unicorn,"https://youtu.be/YeCsjdSsh6U?si=JBE4BsmuJuie4Zth"),
            PaintItem(R.drawable.elefant,"https://youtu.be/85cPylunkXU?si=jUxtsTeAXLcSAQCC"),
            PaintItem(R.drawable.butterfly,"https://youtu.be/oVZwLW7t1hQ?si=lB637b3_K7OCIz3s"),
        )
    }




}

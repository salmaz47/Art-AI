package com.salmee.artai.data



import com.salmee.artai.R
import com.salmee.artai.model.DrawingItem
import com.salmee.artai.model.Habit

object MockHabitsData {
    fun getMorningHabits(): List<Habit> {
        return listOf(
            Habit(
                1,
                "Get Dressed",
                "Get Dressed",
                R.drawable.mh_make_bed),
            Habit(
                2,
                "Learning Through Reading",
                "A boy is sitting on the floor, happily reading a book with a stack of other books beside him. He appears engaged and interested, showing a love for learning. Reading is a great way to gain knowledge, improve language skills, and develop imagination and creativity.",
                R.drawable.mh_study),
            Habit
                (
                3,
                "Get Dressed",
                "Brushing Teeth Before Bedtime\n" +
                    "A little boy in blue pajamas is brushing his teeth at the sink, making sure his teeth are clean before going to sleep. Good oral hygiene at night helps prevent cavities and keeps teeth strong and healthy.",
                R.drawable.mh_brush_teeth),
            Habit(
                4,
                "Take a shower",
                "Starting the day with a refreshing shower is a great habit for YOU! It help you wake up, feel energized, and stay clean. " +
                        "A morning shower can also improve focus and set a positive tone for the day. \uD83D\uDEBF\uD83D\uDE0A",
                R.drawable.mh_take_path),
            Habit(
                5,
                "Enjoying a Delicious Meal",
                "A young boy is sitting at a table, excitedly holding a spoon and fork in his hands, ready to enjoy his meal. The table is covered with a purple tablecloth and has various food items, including bread, soup, and juice. Eating well-balanced meals is essential for maintaining good health and energy throughout the day",
                R.drawable.mh_breakfast),

        )
    }
    fun getNightHabits(): List<Habit> {
        return listOf(
            Habit(
                7,
                "ending the Day with a Healthy Dinner",
                "A young girl is sitting at a table, enjoying a healthy dinner that includes fruits, bread, and a bowl of cereal, along with a glass of juice. Eating a well-balanced meal in the evening is important to maintain energy levels and promote restful sleep.",
                R.drawable.nh_dinner),
            Habit(
                8,
                "Brushing Teeth Before Bedtime",
                "A little boy in blue pajamas is brushing his teeth at the sink, making sure his teeth are clean before going to sleep. Good oral hygiene at night helps prevent cavities and keeps teeth strong and healthy.",
                R.drawable.nh_brush_teeth),
            Habit(
                  9
                , "A Relaxing Shower Before Sleep",
                  "A child is happily taking a shower, covered in bubbles while scrubbing their hair. A warm shower before bed helps to relax the body, wash away the day’s dirt, and prepare for a comfortable night’s sleep",
                R.drawable.nh_take_shower),
            Habit(
                10,
                "Getting Ready for Bed",
                "A boy is standing in front of an open wardrobe, putting on a cozy scarf, looking content as he prepares for the night. Choosing warm and comfortable sleepwear ensures a peaceful and restful sleep",
                R.drawable.nh_change_clothes)
        )
    }
    fun getAroundmeHabits(): List<Habit> {
        return listOf(
            Habit(
                7,
                "Sorting and Folding Laundry",
                "A mother and daughter are sorting colorful clothes into a laundry basket. Helping with laundry teaches children the importance of cleanliness and organization in daily life.",
                R.drawable.ah_lundary),
            Habit(
                8,
                "Washing Dishes After a Mea",
                "A young girl and her mother are washing and drying dishes at the sink. Learning to clean up after meals fosters responsibility and good hygiene habits from an early age",
                R.drawable.ah_washing_dishes),
            Habit(
                9,
                "Tidying Up the Play Area",
                "A little girl is carefully putting her toys into a storage box. Cleaning up after playtime helps children learn the importance of keeping their space neat and clutter-free.",
                R.drawable.ah_collect_toys),
            Habit(
                10,
                "Cooking Together as a Family",
                "A mother and her children are in the kitchen, wearing chef hats and preparing food together. Cooking as a family teaches kids essential life skills while creating fun and memorable moments",
                R.drawable.ah_cooking_with_mpmom),
        )
    }
    fun getSportsTeam(): List<Habit> {
        return listOf(

            Habit(
                10,
                "Playing Soccer on the Field",
                "A soccer player in a yellow jersey is kicking a ball with power and precision. Teamwork, strategy, and agility are key elements of this fast-paced sport.",
                R.drawable.sport7),
            Habit(
                12,
                "Shooting a Basketball",
                "A basketball player in a red jersey is leaping toward the hoop, aiming for a slam dunk. Basketball requires speed, coordination, and teamwork to score points and win games.",
                R.drawable.sport6),
            Habit(
                10,
                "Spiking a Volleyball",
                "Players are engaged in an intense volleyball match, jumping to block and spike the ball. Volleyball is a sport that builds teamwork, communication, and athletic skills.",
                R.drawable.sport4),
            Habit(
                9,
                "Throwing a Handball",
                "A handball player is mid-air, preparing to throw the ball towards the goal with precision. Handball is a dynamic sport that combines speed, agility, and team coordination.",
                R.drawable.sport3),


        )
    }
    fun getSportsIndividual(): List<Habit> {
        return listOf(
            Habit(
                11,
                "Sleep Early",
                "Get Dressed",
                R.drawable.sport5),
            Habit(
                8,
                "Take a Shower",
                "Get Dressed",
                R.drawable.sport2),
            Habit(
                7,
                "Read a Book",
                "Get Dressed",
                R.drawable.sport1),
            )
    }
    fun getSportsMaterialArts(): List<Habit> {
        return listOf(
            Habit(
                11,
                "Sleep Early",
                "Get Dressed",
                R.drawable.sport_material_art1),
            Habit(8,
                "Take a Shower",
                "Get Dressed",
                R.drawable.sport_material_art2),
            Habit(7,
                "Read a Book",
                "Get Dressed",
                R.drawable.sport_material_art3),
            Habit(7,
                "Read a Book",
                "Get Dressed",
                R.drawable.sport_material_art4),
        )
    }
    fun getSportsArtistic(): List<Habit> {
        return listOf(
            Habit(
                11,
                "Sleep Early",
                "Get Dressed",
                R.drawable.sport_artistic1),
            Habit(11,
                "Sleep Early",
                "Get Dressed",
                R.drawable.sport_artistic2),

        )
    }
    fun getStory(): List<Habit> {
        return listOf(
            Habit(
                21,
                "The Brave Little Squirrel",
                "Once upon a time, in a big green forest, there was a little squirrel named Benny. Benny was small but very curious. One day, a strong wind blew, and Benny’s favorite acorn fell into the river. He was scared, but he knew he had to get it back.\n" +
                    "Benny bravely climbed a tree and jumped onto a floating log. The river was fast, but Benny held on tight. With one big leap, he grabbed his acorn and landed safely on the riverbank!\n" +
                    "The other animals cheered, and Benny learned that courage isn’t about being big—it’s about believing in yourself!",
                R.drawable.story_sengab),
            Habit(
                22,
                "The Lion and Tiny Mouse",
                "One day, a mighty lion was sleeping under a tree when a tiny mouse accidentally ran across his paw. The lion woke up, grabbed the mouse, and roared, “How dare you wake me up?”\n" +
                    "The little mouse trembled but bravely said, “Please let me go! One day, I might help you.”\n" +
                    "The lion laughed but let the mouse go.\n" +
                    "A few days later, the lion got caught in a hunter’s net. He roared for help, and the tiny mouse came running. Using his sharp teeth, he chewed through the ropes and freed the lion!\n" +
                    "The lion smiled and said, “Little friend, I see now that even the smallest can be the bravest!”",
                R.drawable.story_lion),
            Habit(
                23,
                "The Magic Paintbrush",
                "Mia was a kind girl who loved to paint. One day, she found a magical paintbrush in the forest. When she painted a flower, it became real! She was amazed and decided to help people with her gift.\n" +
                    "She painted food for the hungry, warm clothes for the cold, and toys for children. But a greedy king heard about her brush and demanded she paint gold for him. Mia refused.\n" +
                    "Angry, the king tried to take the brush, but Mia painted a strong wind that blew him far away! She continued using her gift to bring happiness to others.",  R.drawable.story_brush),
            Habit(
                24,
                "The King Giraffe",
                "In a sunny jungle, there was a tall giraffe named Lila. She loved to help others, but because she was so tall, the other animals thought she couldn't understand their problems.\n" +
                    "One day, a little rabbit lost her way in the tall grass. The animals searched everywhere but couldn't find her. Lila stretched her long neck and looked over the trees. \"I see her!\" she said and guided the rabbit back home.\n" +
                    "From that day on, everyone knew that being different can be a superpower!",
                R.drawable.story_giraff),
            Habit(
                25,
                "The Lazy Bumblebee",
                "Buzzy the bee loved to sleep all day instead of collecting nectar. \"I’ll do it tomorrow,\" he always said.\n" +
                    "One day, a big storm arrived, and Buzzy had no food! He flew to his friends, but they were too busy storing their own honey. Buzzy realized his mistake and promised to work hard from then on.\n" +
                    "After the storm, he collected nectar every day and never went hungry again.",
                R.drawable.story_bee),
            Habit(
                26,
                "The Ant and The Dove",
                "One hot day, a little ant went to drink water from a river but slipped and fell in!\n" +
                    "A kind dove sitting on a tree saw the ant struggling. The dove dropped a leaf into the water, and the ant climbed onto it and reached the shore safely.\n" +
                    "A few days later, a hunter was about to catch the dove with his net. The ant saw this and quickly bit the hunter’s foot! The hunter yelled in pain, and the dove flew away safely.",
                R.drawable.story_ant),
        )
    }
    fun getLetters(): List<Habit> {
        return listOf(
            Habit(11, "Apple","Get Dressed",  R.drawable.a),
            Habit(11, "Blueberry","Get Dressed",  R.drawable.b),
            Habit(11, "Coconut","Get Dressed",  R.drawable.c),
            Habit(11, "DragonFruit","Get Dressed",  R.drawable.d),
            Habit(11, "Elderberry","Get Dressed",  R.drawable.e),
            Habit(11, "Fig","Get Dressed",  R.drawable.f),
            Habit(11, "Grapefruit","Get Dressed",  R.drawable.g),
            Habit(11, "Hazelnut","Get Dressed",  R.drawable.h),
            Habit(11, "Ita Palm","Get Dressed",  R.drawable.i),
            Habit(11, "Jackfruit","Get Dressed",  R.drawable.j),
            Habit(11, "Kiwi","Get Dressed",  R.drawable.k),
            Habit(11, "Lemon","Get Dressed",  R.drawable.l),
            Habit(11, "Mango","Get Dressed",  R.drawable.m),
            Habit(11, "Nectarine","Get Dressed",  R.drawable.n),
            Habit(11, "Orange","Get Dressed",  R.drawable.o),
            Habit(11, "Pineapple","Get Dressed",  R.drawable.p),
            Habit(11, "Quince","Get Dressed",  R.drawable.q),
            Habit(11, "Raspberry","Get Dressed",  R.drawable.r),
            Habit(11, "Strawberry","Get Dressed",  R.drawable.s),
            Habit(11, "Tomato","Get Dressed",  R.drawable.t),
            Habit(11, "Ugli Fruit","Get Dressed",  R.drawable.u),
            Habit(11, "To","Get Dressed",  R.drawable.v),
            Habit(11, "Watermelon","Get Dressed",  R.drawable.w),
            Habit(11, "Ximenia","Get Dressed",  R.drawable.x),
            Habit(11, "Yali Pear","Get Dressed",  R.drawable.y),
            Habit(11, "Vanilla Fruit","Get Dressed",  R.drawable.z),
            )
    }

    fun getNumbers(): List<Habit> {
        return listOf(

            Habit(10, "One","Get Dressed",  R.drawable.one),
            Habit(10, "Two","Get Dressed",  R.drawable.tw0),
            Habit(10, "Three","Get Dressed",  R.drawable.three),
            Habit(10, "Four","Get Dressed",  R.drawable.four),
            Habit(10, "Five","Get Dressed",  R.drawable.five),
            Habit(10, "Six","Get Dressed",  R.drawable.six),
            Habit(10, "Seven","Get Dressed",  R.drawable.seven),
            Habit(10, "Eight","Get Dressed",  R.drawable.eight),
            Habit(10, "Nine","Get Dressed",  R.drawable.nine),
            Habit(10, "Ten","Get Dressed",  R.drawable.ten),



            )
    }
    fun getShapes(): List<Habit> {
        return listOf(
            Habit(11, "Circle","Get Dressed",  R.drawable.circle),
            Habit(11, "Triangle","Get Dressed",  R.drawable.triangle),
            Habit(11, "Square","Get Dressed",  R.drawable.square),
            Habit(11, "Rectangle","Get Dressed",  R.drawable.rectangle),
            Habit(11, "Rectangle","Get Dressed",  R.drawable.star),
            Habit(11, "Rhombus","Get Dressed",  R.drawable.rhombus),


            )
    }
    fun getDrawings(): List<DrawingItem>
    {
        return listOf(
            DrawingItem(R.drawable.puppy,"https://youtu.be/g55jWHQdq0c?si=EEiZibJwv-JUaD6g"),
            DrawingItem(R.drawable.cat,"https://youtu.be/8GPOPkh1rLc?si=DxJfL8QJNV4m47j3"),
            DrawingItem(R.drawable.lion,"https://youtu.be/wCfm-SH-rww?si=JFco-aDZQLY_5y3e"),
            DrawingItem(R.drawable.unicorn,"https://youtu.be/YeCsjdSsh6U?si=JBE4BsmuJuie4Zth"),
            DrawingItem(R.drawable.elefant,"https://youtu.be/85cPylunkXU?si=jUxtsTeAXLcSAQCC"),
            DrawingItem(R.drawable.butterfly,"https://youtu.be/oVZwLW7t1hQ?si=lB637b3_K7OCIz3s"),
        )
    }




}

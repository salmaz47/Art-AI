package com.salmee.artai.data



import com.salmee.artai.R
import com.salmee.artai.model.PaintItem
import com.salmee.artai.model.Habit

object MockHabitsData {
    fun getMorningHabits(): List<Habit> {
        return listOf(
            Habit(
                1,
                "Setting up your bed",
                "A young boy is sitting up in bed, wrapped in a yellow blanket, as he wakes up in the morning. His bed has a cozy setup with pillows and a purple frame. Waking up marks the start of a new day, and a good morning routine helps improve energy, focus, and productivity.",
                R.drawable.mh_make_bed),
            Habit(
                2,
                "Learning Through Reading",
                "A boy is sitting on the floor, happily reading a book with a stack of other books beside him. He appears engaged and interested, showing a love for learning. Reading is a great way to gain knowledge, improve language skills, and develop imagination and creativity.",
                R.drawable.mh_study),
            Habit
                (
                3,
                "Brushing Teeth for a Healthy Smile",
                    "A young girl with brown pigtails is standing at the sink, brushing her teeth while holding a cup in her other hand. She is wearing a green t-shirt and appears focused on maintaining good oral hygiene. Brushing teeth is an essential part of daily self-care, helping to keep teeth clean, prevent cavities, and promote fresh breath.",
                R.drawable.mh_brush_teeth),
            Habit(
                4,
                "Refreshing Morning Shower",
                "A boy is happily taking a shower, scrubbing his hair with shampoo while covered in bubbles. The showerhead is running, creating a relaxing and refreshing moment. Showering is an important hygiene practice that helps remove dirt, sweat, and germs, keeping the body fresh and clean for the day ahead.",
                R.drawable.mh_take_path),
            Habit(
                5,
                "Enjoying a Delicious Meal",
                "A young boy is sitting at a table, excitedly holding a spoon and fork in his hands, ready to enjoy his meal. The table is covered with a purple tablecloth and has various food items, including bread, soup, and juice. Eating well-balanced meals is essential for maintaining good health and energy throughout the day",
                R.drawable.mh_breakfast),
            Habit(
                5,
                "Get Dressed",
                "A mother is kneeling next to a small child, assisting them in getting dressed in front of an open wardrobe. The shelves contain neatly arranged books and other items. Teaching children how to dress themselves is an important step in their development, promoting independence and self-care skills.",
                R.drawable.mh_change_clothes),

        )
    }
    fun getNightHabits(): List<Habit> {
        return listOf(
            Habit(
                7,
                "Enjoying a Healthy Dinner",
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
                "Washing Dishes After a Meal",
                "A young girl and her mother are washing and drying dishes at the sink. Learning to clean up after meals fosters responsibility and good hygiene habits from an early age",
                R.drawable.ah_washing_dishes),
            Habit(
                9,
                "Tidying Up the Play Area",
                "A little girl is carefully putting her toys into a storage box. Cleaning up after playtime helps children learn the importance of keeping their space neat and clutter-free.",
                R.drawable.ah_collect_toys),
            Habit(
                11,
                "Keep the House Clean and Tidy",
                "A mother and child are dusting furniture and vacuuming the floor. Working together to clean the house encourages teamwork and helps maintain a neat and comfortable living space.",
                R.drawable.ah_help_mom_incleaning),
            Habit(
                12,
                "Cooking Together as a Family",
                "A mother and her children are in the kitchen, wearing chef hats and preparing food together. Cooking as a family teaches kids essential life skills while creating fun and memorable moments",
                R.drawable.ah_cooking_with_mpmom),
            Habit(
                13,
                "Organizing Closthes in the Closet",
                "Two children are happily folding and arranging clothes inside a wardrobe. Keeping clothes neat and organized helps children develop independence and a sense of responsibility.",
                R.drawable.ah_organizing_closet),
        )
    }
    fun getSportsTeam(): List<Habit> {
        return listOf(

            Habit(
                10,
                "Football",
                " Football improves cardiovascular health, builds lower-body strength, enhances coordination and agility, and promotes teamwork and communication skills. It also helps develop discipline and strategic",
                R.drawable.sport7),
            Habit(
                12,
                "Basketball",
                "A basketball player is soaring through the air for a slam dunk, showing explosive power and agility.\n" +
                        "Basketball is a high-intensity sport that improves cardiovascular health, builds muscular strength, and enhances coordination and balance. It promotes teamwork, quick decision-making, and boosts mental focus while also being a fun and social way to stay active.",
                R.drawable.sport6),
            Habit(
                10,
                "Volleyball",
                "Two players are jumping high at the net, actively engaged in a competitive volleyball match.\n" +
                        "Volleyball boosts hand-eye coordination, builds upper-body and core strength, improves balance and flexibility, and encourages teamwork. It also enhances quick decision-making and social interaction.",
                R.drawable.sport4),
            Habit(
                9,
                "Handball",
                "A handball player is mid-jump, preparing to throw the ball with speed and precision toward the goal.\n" +
                        "Handball is an intense, fast-paced game that increases aerobic fitness, hand-eye coordination, and agility. It strengthens upper and lower body muscles, improves reflexes, and encourages strategic thinking and communication within a team.",
                R.drawable.sport3),


        )
    }
    fun getSportsIndividual(): List<Habit> {
        return listOf(
            Habit(
                11,
                "Swimming",
                "A swimmer is gliding through the water using powerful freestyle strokes.\n" +
                        "Swimming is a full-body workout that strengthens muscles, improves heart and lung function, increases flexibility, and reduces stress. It’s a low-impact activity suitable for all ages, making it great for overall fitness and mental well-being.",
                R.drawable.sport5),
            Habit(
                8,
                "Tennis",
                "A tennis player is in motion, ready to strike the ball with a forehand swing.\n" +
                        "Tennis provides a powerful combination of cardio, strength, and flexibility training. It enhances balance, coordination, and reaction time while building endurance and muscular tone. The game also supports mental sharpness through strategic play and quick decision-making.",
                R.drawable.sport2),
            Habit(
                7,
                "Badminton",
                "Badminton is a dynamic sport that offers numerous physical, mental, and social benefits. It improves cardiovascular health, builds muscle strength, enhances reflexes, aids weight management, reduces stress, sharpens focus, fosters discipline, and promotes teamwork. With minimal equipment requirements, it's accessible to all ages and skill levels.",
                R.drawable.sport1),
            )
    }
    fun getSportsMaterialArts(): List<Habit> {
        return listOf(
            Habit(
                11,
                "Karate",
                "Karate is a competitive sport and traditional martial art that offers physical, mental, and social benefits. It enhances strength, flexibility, reflexes, cardiovascular health, mental discipline, focus, self-control, and focus. Its belt-ranking system promotes goal-setting and perseverance, while kata and kumite develop precision and adaptability.",
                R.drawable.sport_material_art1),
            Habit(8,
                "Taekwondo",
                "Taekwondo is a martial art that enhances physical fitness, mental discipline, confidence, and self-defense skills through dynamic kicks and strikes. It emphasizes speed, agility, and high-flying techniques, making it an effective combat system and thrilling spectator sport. Taekwondo instills core values of respect, perseverance, and indomitable spirit through its belt ranking system and competitive opportunities.",
                R.drawable.sport_material_art2),
            Habit(7,
                "Judo",
                "Judo is a transformative martial art that builds strength, agility, and endurance, teaches self-defense, cultivates discipline, and fosters humility. As an Olympic sport, it offers competitive goals and lifelong values, empowering individuals of all ages to grow stronger in body, mind, and spirit.",
                R.drawable.sport_material_art3),
            Habit(7,
                "Boxing",
                "Boxing offers physical, mental, and social benefits beyond combat. It improves cardiovascular health, builds strength, burns calories, sharpens reflexes, and enhances self-defense skills. Mentally, it cultivates discipline, stress relief, and confidence. Boxing fosters camaraderie and offers competitive pathways, making it a comprehensive practice.",
                R.drawable.sport_material_art4),
        )
    }
    fun getSportsArtistic(): List<Habit> {
        return listOf(
            Habit(
                11,
                "Gymnastics",
                "Gymnastics is a transformative sport that develops physical strength, flexibility, coordination, mental discipline, confidence, and creativity. It promotes full-body conditioning, bone health, injury prevention, and life skills like perseverance, focus, teamwork, and artistic expression. It enhances performance in other athletic pursuits.",
                R.drawable.sport_artistic1),
            Habit(11,
                "Ballet",
                "Ballet is a demanding art form that enhances physical strength, flexibility, endurance, mental discipline, artistic expression, and emotional resilience. It builds musculature, improves posture, and enhances cardiovascular health. Ballet also instills life skills like focus, perseverance, teamwork, and creative confidence.",
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

package org.pipepipe.plugin.edutube.data;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import java.util.ArrayList;
import java.util.List;

/**
 * Curated repository of verified, high-impact academic courses, university lectures,
 * and comprehensive tutorials across STEM, Computer Science, and Humanities.
 */
public final class CuratedCourses {

    private CuratedCourses() {
    }

    public static List<InfoItem> getUniversityCourses(final int serviceId) {
        final List<InfoItem> list = new ArrayList<>();

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=7UJ4CFRGd-U",
                "MIT 18.06 Linear Algebra - Lecture 1: The Geometry of Linear Equations (Prof. Gilbert Strang)",
                "MIT OpenCourseWare",
                "7UJ4CFRGd-U",
                2389,
                5400000,
                "MIT OpenCourseWare foundational Linear Algebra course taught by Prof. Gilbert Strang."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=ZA-tUyM_y7s",
                "MIT 6.006 Introduction to Algorithms - Lecture 1: Algorithms and Computation",
                "MIT OpenCourseWare",
                "ZA-tUyM_y7s",
                3140,
                3800000,
                "MIT introduction to mathematical modeling of computational problems and algorithms."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=LfaMVlDaQ24",
                "Harvard CS50 - Introduction to Computer Science (Full University Lecture Series)",
                "CS50",
                "LfaMVlDaQ24",
                9120,
                6200000,
                "An introduction to the intellectual enterprises of computer science and the art of programming."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=jGwO_UgTS7I",
                "Stanford CS229: Machine Learning - Lecture 1 (Prof. Andrew Ng)",
                "Stanford Online",
                "jGwO_UgTS7I",
                4680,
                4200000,
                "Stanford university graduate course in machine learning and pattern recognition by Andrew Ng."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=4a0FbQdH3dY",
                "MIT 8.01 Classical Mechanics - Lecture 1: Powers of Ten, Units, Dimensions (Prof. Walter Lewin)",
                "MIT OpenCourseWare",
                "4a0FbQdH3dY",
                3002,
                2900000,
                "Classical mechanics, kinematics, Newton's laws, gravitation, and conservation laws."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=P_24kNpyCsv",
                "Yale Courses - Introduction to Psychology - Lecture 1 (Prof. Paul Bloom)",
                "YaleCourses",
                "P_24kNpyCsv",
                2980,
                3100000,
                "What do your dreams mean? What happens during a seizure? Exploration of the human mind."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=kBdfcR-8hEY",
                "Harvard University - Justice with Michael Sandel - Episode 01: The Moral Side of Murder",
                "Harvard University",
                "kBdfcR-8hEY",
                3280,
                15000000,
                "Harvard's famous undergraduate course exploring morality, justice, and ethics in society."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=zkLpHdzmsbg",
                "MIT 18.01 Single Variable Calculus - Lecture 1: Derivatives and Rate of Change",
                "MIT OpenCourseWare",
                "zkLpHdzmsbg",
                3110,
                1900000,
                "Differentiation and integration of functions of one variable with applications."
        ));

        list.add(createPlaylist(
                serviceId,
                "https://www.youtube.com/playlist?list=PLUl4u3cNGP61Oq3tWYp6V_F-5jb5L2iHb",
                "MIT 6.0001 Introduction to Computer Science and Programming in Python",
                "MIT OpenCourseWare",
                12
        ));

        list.add(createPlaylist(
                serviceId,
                "https://www.youtube.com/playlist?list=PL848F2368C90DDC3D",
                "Stanford University - Programming Methodology (CS106A)",
                "Stanford Online",
                28
        ));

        return list;
    }

    public static List<InfoItem> getComputerScienceCourses(final int serviceId) {
        final List<InfoItem> list = new ArrayList<>();

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=_uQrJ0TkZlc",
                "Python Full Course for Beginners - Complete University Level Tutorial",
                "freeCodeCamp.org",
                "_uQrJ0TkZlc",
                15960,
                45000000,
                "Comprehensive Python course covering basics, OOP, data structures, and practical projects."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=vLnPwxZdW4Y",
                "C++ Programming Course - Beginner to Advanced Complete Bootcamp",
                "freeCodeCamp.org",
                "vLnPwxZdW4Y",
                11220,
                12000000,
                "Master modern C++ with low-level memory management, pointers, templates, and algorithms."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=RBSGKlAnoiM",
                "Data Structures and Algorithms for Technical Interviews - Masterclass",
                "freeCodeCamp.org",
                "RBSGKlAnoiM",
                18900,
                8200000,
                "Big O notation, linked lists, trees, graphs, dynamic programming, and heaps."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=Z1Yd7upQsXY",
                "Rust Programming Language - Comprehensive Course for Software Engineers",
                "freeCodeCamp.org",
                "Z1Yd7upQsXY",
                19800,
                4100000,
                "Learn Rust fundamentals, ownership, borrowing, lifetimes, error handling, and concurrency."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=H7pm42xnh8M",
                "Distributed Systems and Backend Architecture - Fundamentals (Hussein Nasser)",
                "Hussein Nasser",
                "H7pm42xnh8M",
                4200,
                950000,
                "Deep architectural exploration of network protocols, gRPC, reverse proxies, and caching."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=1uHJpS_8n6Q",
                "The C++ Game Engine Architecture Series - Fundamentals of Engine Design",
                "The Cherno",
                "1uHJpS_8n6Q",
                2580,
                890000,
                "Professional guide to building an enterprise-grade C++ game engine from scratch."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=qwAFL1597eM",
                "Computer Architecture: How Computers Calculate Numbers (Ben Eater)",
                "Ben Eater",
                "qwAFL1597eM",
                1240,
                2100000,
                "Building an 8-bit breadboard computer with TTL logic gates, registers, and ALU."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=yE9v9tefsdU",
                "System Design for Beginners - Course by ByteByteGo",
                "ByteByteGo",
                "yE9v9tefsdU",
                3600,
                1800000,
                "Scalability, load balancing, database sharding, consistency models, and caching strategies."
        ));

        return list;
    }

    public static List<InfoItem> getStemCourses(final int serviceId) {
        final List<InfoItem> list = new ArrayList<>();

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=fNk_zzaMoSs",
                "3Blue1Brown - Vectors | Chapter 1, Essence of Linear Algebra",
                "3Blue1Brown",
                "fNk_zzaMoSs",
                591,
                8700000,
                "Geometric intuition behind vectors, linear combinations, span, and basis vectors."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=WUvTyaaNkzM",
                "3Blue1Brown - The Essence of Calculus | Chapter 1",
                "3Blue1Brown",
                "WUvTyaaNkzM",
                1025,
                9100000,
                "Visual introduction to derivatives, limits, and the fundamental theorem of calculus."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=aircAruvnKk",
                "3Blue1Brown - But what is a neural network? | Deep learning, chapter 1",
                "3Blue1Brown",
                "aircAruvnKk",
                1153,
                16000000,
                "Mathematical structure, weights, biases, and activation functions of multilayer perceptrons."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=fYyARMqiaag",
                "Calculus 1 - Full College Course (Prof. Leonard)",
                "Professor Leonard",
                "fYyARMqiaag",
                9720,
                3900000,
                "Complete university lecture series covering limits, continuity, derivatives, and integrals."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=7kJ1n_0mU_Y",
                "StatQuest - Machine Learning Fundamentals: Bias and Variance",
                "StatQuest with Josh Starmer",
                "7kJ1n_0mU_Y",
                395,
                2400000,
                "Clearly explained statistical concepts of underfitting, overfitting, and model validation."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=Aof0w87x190",
                "PBS Space Time - The True Nature of Quantum Spin",
                "PBS Space Time",
                "Aof0w87x190",
                912,
                1800000,
                "Exploring quantum mechanics, wave function symmetry, Pauli exclusion, and spinors."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=d_k8wQ_f6X8",
                "Veritasium - The Simplest Math Problem No One Can Solve (Collatz Conjecture)",
                "Veritasium",
                "d_k8wQ_f6X8",
                1320,
                41000000,
                "Deep mathematical investigation into unsolved dynamical systems and recurrence relations."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=bBC-nXj3Ng4",
                "Numberphile - The Riemann Hypothesis Explained",
                "Numberphile",
                "bBC-nXj3Ng4",
                980,
                5200000,
                "Complex analysis, zeta function zeros, and the distribution of prime numbers."
        ));

        return list;
    }

    public static List<InfoItem> getEngineeringCourses(final int serviceId) {
        final List<InfoItem> list = new ArrayList<>();

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=UqQ3Z0yG1c0",
                "Real Engineering - How Rockets Are Built: Materials and Metallurgy",
                "Real Engineering",
                "UqQ3Z0yG1c0",
                1210,
                2900000,
                "Aerospace engineering, cryogenic propellant tanks, and titanium-nickel superalloys."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=m743mBqj_38",
                "Practical Engineering - How Electrical Substation Grids Work",
                "Practical Engineering",
                "m743mBqj_38",
                890,
                3800000,
                "High voltage transmission, circuit breakers, step-down transformers, and grid stability."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=xZ_pQkC5g6M",
                "SmarterEveryDay - Laminar Flow vs Turbulent Fluid Dynamics in Physics",
                "SmarterEveryDay",
                "xZ_pQkC5g6M",
                980,
                18000000,
                "Navier-Stokes equations, Reynolds numbers, and boundary layer fluid mechanics."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=8mVeA8e1b3I",
                "NileRed - Making Aerogel: The World's Lightest Solid Material",
                "NileRed",
                "8mVeA8e1b3I",
                1840,
                14000000,
                "Supercritical drying, chemical silica synthesis, and high-vacuum laboratory processes."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=3eZz8v_v_60",
                "Two Minute Papers - Revolutionary Physics Simulation Engines in Real Time",
                "Two Minute Papers",
                "3eZz8v_v_60",
                340,
                890000,
                "Review of breakthroughs in neural rendering, fluid simulation, and particle physics."
        ));

        return list;
    }

    public static List<InfoItem> getHumanitiesCourses(final int serviceId) {
        final List<InfoItem> list = new ArrayList<>();

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=Yocja_N5s1I",
                "CrashCourse World History - The Agricultural Revolution: Crash Course #1",
                "CrashCourse",
                "Yocja_N5s1I",
                680,
                14000000,
                "The shift from foraging to farming, surplus food production, and early civilizations."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=3ez10ADR_gM",
                "CrashCourse Economics - Intro to Economics: Crash Course Econ #1",
                "CrashCourse",
                "3ez10ADR_gM",
                725,
                6800000,
                "Scarcity, opportunity cost, supply, demand, and foundational macroeconomic theories."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=1A_CAkYt3GY",
                "CrashCourse Philosophy - What is Philosophy? Crash Course Philosophy #1",
                "CrashCourse",
                "1A_CAkYt3GY",
                635,
                7200000,
                "Introduction to logic, metaphysics, ethics, and critical reasoning in philosophy."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=R9OHn5ZF4Uo",
                "Fall of Civilizations - The Bronze Age Collapse: Fire & Forged Iron",
                "Fall of Civilizations",
                "R9OHn5ZF4Uo",
                8280,
                5100000,
                "A comprehensive deep documentary on the collapse of Mycenae, Hatti, and the Near East."
        ));

        list.add(createCourse(
                serviceId,
                "https://www.youtube.com/watch?v=j_KzC8T68oU",
                "Academy of Ideas - Carl Jung and the Shadow: The Mechanics of the Unconscious",
                "Academy of Ideas",
                "j_KzC8T68oU",
                810,
                4300000,
                "Depth psychology, shadow integration, individuation, and Jungian archetypes."
        ));

        return list;
    }

    private static StreamInfoItem createCourse(final int serviceId,
                                               final String url,
                                               final String title,
                                               final String uploader,
                                               final String videoId,
                                               final long durationSeconds,
                                               final long views,
                                               final String description) {
        final StreamInfoItem item = new StreamInfoItem(serviceId, url, title, StreamType.VIDEO_STREAM);
        item.setUploaderName(uploader);
        item.setThumbnailUrl("https://i.ytimg.com/vi/" + videoId + "/hqdefault.jpg");
        item.setDuration(durationSeconds);
        item.setViewCount(views);
        item.setShortDescription(description);
        return item;
    }

    private static PlaylistInfoItem createPlaylist(final int serviceId,
                                                   final String url,
                                                   final String title,
                                                   final String uploader,
                                                   final long count) {
        final PlaylistInfoItem item = new PlaylistInfoItem(serviceId, url, title);
        item.setUploaderName(uploader);
        item.setStreamCount(count);
        return item;
    }
}

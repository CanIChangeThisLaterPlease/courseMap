package com.example.coursemap

class Node(val courseId: String, val courseName: String, var complete: Boolean = false)

class Edge(val source: Node, val destination: Node, var complete: Boolean = false)


class Graph(val nodes: List<Node>, val edges: List<Edge>)

class GraphManager {
    private val majorGraphs = mapOf(
        "Computer Science" to lazy { createComputerScienceGraph() },
//        "Information Systems" to lazy { createInformationSystemsGraph() },

    )

    fun getGraphForMajor(majorName: String): Graph? {
        val graphProvider= majorGraphs[majorName]
        return graphProvider?.value
    }

    private fun createComputerScienceGraph(): Graph {
        val nodes = listOf(
            Node("MATH101", "Calculus I"),
            Node("CS201", "Discrete Structures"),
            Node("CS222", "Theory of Algorithms"),
            Node("MATH102", "Calculus II"),
            Node("IE211", "Probability and Statistics"),
            Node("CS330", "Image Understanding"),
            Node("CS356", "Information Security"),
            Node("CS332", "Computer Graphics"),
            Node("CS477", "Mobile Computing"),
            Node("ECE317", "Linear Algebra"),
            Node("CS323", "Computational Theory"),
            Node("CS451", "Artificial Intelligence"),
            Node("CS419", "Compiler Construction"),
            Node("CS116", "Computing Fundamentals"),
            Node("CE212", "Digital Systems"),
            Node("CS415", "Systems Programming"),
            Node("CS223", "Data Structures"),
            Node("CS214", "Object Oriented Programming"),
            Node("CE352", "Computer Networks"),
            Node("CE357", "Operating Systems"),
            Node("CE201", "Architecture and Organization"),
            Node("CS362", "Database Management Systems"),
            Node("CS254", "Visual Programming"),
            Node("CS342", "Software Engineering"),
            Node("CS355", "Web Technologies")
        )

        val edges = listOf(
            Edge(nodes[0], nodes[3]),   // MATH101 -> MATH102
            Edge(nodes[3], nodes[9]),   // MATH102 -> ECE317
            Edge(nodes[3], nodes[4]),   // MATH102 -> IE211
            Edge(nodes[9], nodes[7]),   // ECE317 -> CS332
            Edge(nodes[9], nodes[5]),   // ECE317 -> CS330
            Edge(nodes[1], nodes[2]),   // CS201 -> CS222
            Edge(nodes[2], nodes[10]),  // CS222 -> CS323
            Edge(nodes[2], nodes[11]),  // CS222 -> CS451
            Edge(nodes[2], nodes[12]),  // CS222 -> CS419
            Edge(nodes[13], nodes[2]),  // CS116 -> CS222
            Edge(nodes[13], nodes[15]), // CS116 -> CS223
            Edge(nodes[13], nodes[16]), // CS116 -> CS214
            Edge(nodes[13], nodes[18]), // CS116 -> CE352
            Edge(nodes[14], nodes[20]), // CE212 -> CE201
            Edge(nodes[15], nodes[17]), // CS223 -> CS415
            Edge(nodes[15], nodes[10]), // CS223 -> CS323
            Edge(nodes[15], nodes[11]), // CS223 -> CS451
            Edge(nodes[15], nodes[12]), // CS223 -> CS419
            Edge(nodes[15], nodes[9]),  // CS223 -> CS332
            Edge(nodes[15], nodes[5]),  // CS223 -> CS330
            Edge(nodes[15], nodes[21]), // CS223 -> CS362
            Edge(nodes[16], nodes[22]), // CS214 -> CS254
            Edge(nodes[20], nodes[19]), // CE201 -> CE357
            Edge(nodes[20], nodes[18]), // CE201 -> CE352
            Edge(nodes[21], nodes[23]), // CS362 -> CS342
            Edge(nodes[21], nodes[24]), // CS362 -> CS355
            Edge(nodes[21], nodes[8]),  // CS362 -> CS477
            Edge(nodes[21], nodes[6]),  // CS362 -> CS356
            Edge(nodes[22], nodes[23]), // CS254 -> CS342
            Edge(nodes[22], nodes[24]), // CS254 -> CS355
            Edge(nodes[22], nodes[8]))   // CS254 -> CS477
        return Graph(nodes, edges)
    }

//    private fun createInformationSystemsGraph(): Graph {
////        val nodes = listOf(
////            Node("4", "Course X"),
////            Node("5", "Course Y"),
////            Node("6", "Course Z")
////        )
////        val edges = listOf(
////            Edge("4", "5"),
////            Edge("5", "6"),
////            Edge("6", "4")
////        )
////        return Graph(nodes, edges)
//    }
}

//// Usage example
//val graphManager = com.example.coursemap.GraphManager()
//
//val majorName = "Computer Science"
//val graph = graphManager.getGraphForMajor(majorName)
//if (graph != null) {
//    // com.example.coursemap.Graph found for the given major
//    println(graph)
//} else {
//    // No graph found for the given major
//    println("No graph found for the major: $majorName")
//}

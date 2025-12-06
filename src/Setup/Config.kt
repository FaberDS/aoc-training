package Setup

data class ConfigForDay(var submit1: Boolean = false,
                        var submit2: Boolean = false,
                        var check1: Boolean = false,
                        var check2: Boolean = false,
                        var checkDemo1: Boolean = false,
                        var checkDemo2: Boolean = false,
                        var execute1: Boolean = false,
                        var execute2: Boolean = false,
                        var execute1demo: Boolean = false,
                        var execute2demo: Boolean = false,
                        var exampleSolution1:String = "0",
                        var exampleSolution2: String = "0",
                        var solution1: String = "0",
                        var solution2: String = "0")

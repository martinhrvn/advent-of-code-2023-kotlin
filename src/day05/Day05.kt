package day05

import println
import readInput

data class Mapping(val source: Long, val destination: Long, val range: Long) {
  fun contains(seed: Long): Boolean {
    return seed in source ..< source + range
  }

  fun getDestination(seed: Long): Long {
    return destination + (seed - source)
  }
}

fun List<Mapping>.getDestination(seed: Long): Long {
  return this.firstOrNull { it.contains(seed) }?.getDestination(seed) ?: seed
}

data class SeedMapping(
    val seeds: List<Long>,
    val seedToSouil: List<Mapping>,
    val souilToFertilizer: List<Mapping>,
    val fertilizerToWater: List<Mapping>,
    val waterToLight: List<Mapping>,
    val ligthToTemperature: List<Mapping>,
    val temperatureToHumidity: List<Mapping>,
    val humidityToLocation: List<Mapping>
) {
  fun getDestination(seed: Long): Long {
    return humidityToLocation.getDestination(
        temperatureToHumidity.getDestination(
            ligthToTemperature.getDestination(
                waterToLight.getDestination(
                    fertilizerToWater.getDestination(souilToFertilizer.getDestination(seedToSouil.getDestination(seed)))))))
  }

    fun getSeedDestinations(): List<Long> {
        return seeds.map { getDestination(it)}
    }

    fun getExtendedSeedDestinations(): List<Long> {
//        val hlMap = humidityToLocation.fold(mapOf<Long,Long>()) { acc, curr ->
//            acc.plus(initMap(curr, acc))
//        }
//        val thMap = rangesToMap(temperatureToHumidity, hlMap)
//        val ltMap = rangesToMap(ligthToTemperature, thMap)
//        val wlMap = rangesToMap(waterToLight, ltMap)
//        val fwMap = rangesToMap(fertilizerToWater, wlMap)
//        val sfMap = rangesToMap(souilToFertilizer, fwMap)
//        val ssMap = rangesToMap(seedToSouil, sfMap)

        return seeds.chunked(2).map { (start, range) ->
            (start..<start+range).minBy { i -> getSeedDestinations().min() }
        }
    }

    companion object {
        fun parse(input: List<String>): SeedMapping {
            val (label, seedStr) = input.first().split(": ")
            val seeds = seedStr.split(" ").map { it.toLong() }
            val seedToSoilMapping = getMapping(input, "seed-to-soil map:" )
            val soilToFertilizer = getMapping( input,"soil-to-fertilizer map:")
            val fertilizerToWater = getMapping( input, "fertilizer-to-water map:")
            val waterToLight = getMapping( input, "water-to-light map:" )
            val ligthToTemperature = getMapping( input, "light-to-temperature map:")
            val temperatureToHumidity = getMapping( input, "temperature-to-humidity map:")
            val humidityToLocation = getMapping( input, "humidity-to-location map:")

            return SeedMapping(seeds, seedToSoilMapping, soilToFertilizer, fertilizerToWater, waterToLight, ligthToTemperature, temperatureToHumidity, humidityToLocation)
        }

        fun rangesToMap(sources: List<Mapping>, destination: Map<Long,Long>): Map<Long, Long> {
            return sources.fold(destination) { acc, curr ->
                acc.plus(rangeToMap(curr, acc))
            }
        }

        fun initMap(source: Mapping, destination: Map<Long, Long>): Map<Long, Long> {
            return (source.destination - source.range..<source.range).mapNotNull { i ->
                (source.source + i) to (source.destination + i)
            }.toMap()
        }


        fun rangeToMap(source: Mapping, destination: Map<Long, Long>): Map<Long, Long> {
            return (source.destination - source.range..<source.range).mapNotNull { i ->
                destination.getOrDefault(source.destination + i, null)?.let {
                    (source.source + i) to it
                }
            }.toMap()
        }

        fun getMapping(input: List<String>, label: String): List<Mapping> {
            return input.dropWhile{ !it.startsWith(label)}.drop(1).takeWhile { "\\d+ \\d+ \\d+".toRegex().matches(it)}.map {
                val (destination, source, range) = it.split(" ")
                Mapping(source.toLong(), destination.toLong(), range.toLong())
            }
        }
    }

}

class SoilChecker(private val input: List<String>) {

  fun part1(): Long {

    return SeedMapping.parse(input).getSeedDestinations().min()
  }

  fun part2(): Long {
    return  SeedMapping.parse(input).getExtendedSeedDestinations().min()
  }
}

fun main() {
  val input = readInput("day05/Day05")
  val soilChecker = SoilChecker(input)
  soilChecker.part1().println()
  soilChecker.part2().println()
}

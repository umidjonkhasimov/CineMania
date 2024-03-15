package uz.john.data.remote.model.person

data class PersonCreditsResponseData(
    val cast: List<CastCreditData>,
    val crew: List<CrewCreditData>
)
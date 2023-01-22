class utilidades {
    private fun cadenafecha(dia: Int, mes: Int, anio: Int): String {
        var sAnio: String = anio.toString()
        var sMes: String = mes.toString()
        var sDia: String = dia.toString()

        if (dia < 10){
            sDia = '0' + dia.toString()
        }
        if (mes < 10){
            sMes = '0' + mes.toString()
        }
        return sDia + '/' + sMes + '/' + sAnio
    }
}
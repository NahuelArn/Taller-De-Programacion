programa jeje
procesos 
  proceso  juntarFlores(ES cantF: numero)
  comenzar
    mientras(HayFlorEnLaEsquina)
      tomarFlor
      cantF:= cantF+1
  fin
  proceso sincronizarme
  variables
    ok: boolean
  comenzar
    EnviarMensaje(ok, jefe)
    RecibirMensaje(ok, jefe)
  fin
  {sarasa}
  proceso asignarIds
  comenzar
    EnviarMensaje(1, bot1)
    EnviarMensaje(2, bot2)
    EnviarMensaje(3, bot3)
  fin
  proceso sincronizacionExitosa
  variables
    ok: boolean
  comenzar
    EnviarMensaje(ok, bot1)
    EnviarMensaje(ok, bot2)
    EnviarMensaje(ok, bot3)
  fin
  proceso sacarMax(E cantF: numero;E id: numero; ES max: numero; ES idMax: numero) 
  comenzar
    Informar('ENTRE',idMax)
    si(cantF > max)
      max:= cantF
      idMax:= id
  fin
  proceso bloquearDepositarLiberar(ES cantF: numero;E avIni: numero; E caIni: numero)
  comenzar
    BloquearEsquina(10,10)
    Pos(10,10)
    depositarFlor
    cantF:= cantF-1
    Pos(avIni,caIni)
    LiberarEsquina(10,10)
  fin
  proceso avisarResultados(E idMax: numero)
  comenzar
    si(idMax = 1)
      EnviarMensaje(V, bot1)
      EnviarMensaje(F, bot2)
      EnviarMensaje(F, bot3)
    sino
      si(idMax = 2)
        EnviarMensaje(F, bot1)
        EnviarMensaje(V, bot2)
        EnviarMensaje(F, bot3)
      sino
        si(idMax = 2)
          EnviarMensaje(F, bot1)
          EnviarMensaje(V, bot2)
          EnviarMensaje(F, bot3)
        sino
          si(idMax = 3)
            EnviarMensaje(F, bot1)
            EnviarMensaje(F, bot2)
            EnviarMensaje(V, bot3)
          sino  {el id es 0 y nadie gano}
            EnviarMensaje(F, bot1)
            EnviarMensaje(F, bot2)
            EnviarMensaje(F, bot3)
  fin
areas
  areaBot1: AreaP(1,1,6,6)
  areaBot2: AreaP(7,1,12,6)
  areaBot3: AreaP(13,1,18,6)
  areaJefe: AreaP(20,1,20,1)
  areaDeposito: AreaPC(10,10,10,10)
robots
  robot  cuadrado
  variables
    cantF: numero
    id,avIni,caIni: numero
    ok: boolean
  comenzar
    RecibirMensaje(id, jefe)
    repetir 4
      repetir 5
        juntarFlores(cantF)
        mover
      juntarFlores(cantF)
      derecha
      sincronizarme
    EnviarMensaje(id, jefe)
    EnviarMensaje(cantF, jefe)
    RecibirMensaje(ok, jefe)  {si gane no hago naty}
    si(~ok)
      avIni:= PosAv
      caIni:= PosCa
      mientras (cantF > 0)
        bloquearDepositarLiberar(cantF,avIni,caIni)
  fin
  robot  coordinador
  variables
    ok: boolean
    id: numero
    max,idMax,cantF: numero
  comenzar
    asignarIds
    max:= -99
    idMax:= 0
    repetir 4
      repetir 3
        RecibirMensaje(ok, *)
      sincronizacionExitosa
    repetir 3
      RecibirMensaje(id, *)
      si(id = 1)
        RecibirMensaje(cantF, bot1)
      sino
        si(id = 2)
          RecibirMensaje(cantF, bot2)
        sino
          RecibirMensaje(cantF, bot3)
      sacarMax(cantF,id,max, idMax) 
    si(idMax <> 0)
      Informar('ELmAXBotfuEee',idMax)
    sino
      Informar('naoMax',0)
    avisarResultados(idMax)
  fin
variables
  bot1, bot2, bot3: cuadrado
  jefe: coordinador
comenzar
  AsignarArea(bot1, areaBot1)  {areas default}
  AsignarArea(bot2, areaBot2)
  AsignarArea(bot3, areaBot3)
  AsignarArea(jefe, areaJefe)
  AsignarArea(bot1, areaDeposito)  {areas deposito por si pierden}
  AsignarArea(bot2, areaDeposito)
  AsignarArea(bot3, areaDeposito)
  Iniciar(bot1, 1,1)
  Iniciar(bot2, 7,1)
  Iniciar(bot3, 13,1)
  Iniciar(jefe, 20,1)
fin
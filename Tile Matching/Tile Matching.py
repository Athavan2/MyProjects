html='''
<style>
table#jeu {
    border-spacing: 10px;
    border-collapse: separate;
    display: inline-block;
}
table#jeu td {
    background-color: #d0d0d0;
    border:1px solid #008CBA;
    width: 100px;
    height: 120px;
    background-position: center;
    background-size: cover;
}

.menu-element {
  margin: 10px;
  border:1px solid #008CBA;
  width: 100%;
}

.level-button {
  padding: 15px 30px;
  background-color: #008CBA;
  opacity: 0.7;
  border: none;
  color: white;
  text-align: center;
  font-size: 16px;
}


#score-box {
  padding: 5px 5px;
  text-align: center;
}

#score-text {
  font-family: OCR A Std, monospace;
}

.score-title {
  font-weight: bold;
}

.texte-meilleur {
  font-size: small;
}

.menu {
  display: inline-block;
  width: 150px;
}
</style>

<link rel="preload" id="link0" as="image" href="https://upload.wikimedia.org/w\
ikipedia/commons/thumb/b/bb/Kittyply_edit1.jpg/320px-Kittyply_edit1.jpg">
<link rel="preload" id="link1" as="image" href="https://upload.wikimedia.org/w\
ikipedia/commons/thumb/4/4f/Felis_silvestris_catus_lying_on_rice_straw.jpg/320\
px-Felis_silvestris_catus_lying_on_rice_straw.jpg">
<link rel="preload" id="link2" as="image" href="https://upload.wikimedia.org/w\
ikipedia/commons/thumb/5/5e/Domestic_Cat_Face_Shot.jpg/320px-Domestic_Cat_Face\
_Shot.jpg">
<link rel="preload" id="link3" as="image" href="https://upload.wikimedia.org/w\
ikipedia/commons/thumb/3/3b/Gato_enervado_pola_presencia_dun_can.jpg/308px-Gat\
o_enervado_pola_presencia_dun_can.jpg">
<link rel="preload" id="link4" as="image" href="https://upload.wikimedia.org/w\
ikipedia/commons/thumb/b/b6/Felis_catus-cat_on_snow.jpg/320px-Felis_catus-cat_\
on_snow.jpg">
<link rel="preload" id="link5" as="image" href="https://upload.wikimedia.org/w\
ikipedia/commons/thumb/9/97/Feral_cat_Virginia_crop.jpg/206px-Feral_cat_Virgin\
ia_crop.jpg">
<link rel="preload" id="link6" as="image" href="https://upload.wikimedia.org/w\
ikipedia/commons/thumb/0/0c/Black_Cat_%287983739954%29.jpg/320px-Black_Cat_%28\
7983739954%29.jpg">
<link rel="preload" id="link7" as="image" href="https://upload.wikimedia.org/w\
ikipedia/commons/thumb/c/cd/Roo_Female_Somali_in_Cat_Caf%C3%A9_Tokyo.jpg/160px\
-Roo_Female_Somali_in_Cat_Caf%C3%A9_Tokyo.jpg">
<link rel="preload" id="link8" as="image" href="https://upload.wikimedia.org/w\
ikipedia/commons/6/6e/Longhairedmunchkin.jpg">
<link rel="preload" id="link9" as="image" href="https://upload.wikimedia.org/w\
ikipedia/commons/thumb/c/ca/Niobe050905-Siamese_Cat.jpeg/179px-Niobe050905-S\
iamese_Cat.jpeg">
<link rel="preload" id="link10" as="image" href="https://upload.wikimedia.org/\
wikipedia/commons/thumb/0/06/Charcoal_Bengal.jpg/320px-Charcoal_Bengal.jpg">
<link rel="preload" id="link11" as="image" href="https://upload.wikimedia.org/\
wikipedia/commons/thumb/b/ba/Paintedcats_Red_Star_standing.jpg/187px-Paintedc\
ats_Red_Star_standing.jpg">
<link rel="preload" id="link12" as="image" href="https://upload.wikimedia.org/\
wikipedia/commons/thumb/1/13/ChausieBGT.jpg/221px-ChausieBGT.jpg">
<link rel="preload" id="link13" as="image" href="https://upload.wikimedia.org/\
wikipedia/commons/thumb/c/c4/Savannah_Cat_portrait.jpg/160px-Savannah_Cat_por\
trait.jpg">
<link rel="preload" id="link14" as="image" href="https://upload.wikimedia.org/\
wikipedia/commons/thumb/0/0b/Plume_the_Cat.JPG/320px-Plume_the_Cat.JPG">
<link rel="preload" id="link15" as="image" href="https://upload.wikimedia.org/\
wikipedia/commons/e/e9/Persian_sand_CAT.jpg">
<link rel="preload" id="link16" as="image" href="https://upload.wiki\
media.org/wikipedia/commons/thumb/2/21/Detalhe_nariz_Osk.jpg/180px-Detalhe\
_nariz_Osk.jpg">
<link rel="preload" id="link17" as="image" href="https://upload.wikimedia.org/\
wikipedia/commons/thumb/3/33/Siam_blue_point.jpg/263px-Siam_blue_point.jpg">
<link rel="preload" id="link18" as="image" href="https://upload.wikimedia.org/\
wikipedia/commons/thumb/0/0e/Felis_silvestris_silvestris.jpg/208px-Felis_silv\
estris_silvestris.jpg">
<link rel="preload" id="link19" as="image" href="https://upload.wikimedia.org/\
wikipedia/commons/f/f7/Prionailurus_viverrinus_01.jpg">

<div>
  <table id="jeu" > </table>

  <div class="menu">
    <div id="score-box" class="menu-element" style="">
      <div class="score-title">SCORE</div>
      <div id="score-text"></div>
    </div>
    <button id="niveauFacile" class="level-button menu-element" \
    onclick="selectionMode(12)">
      <b>Facile</b><br>
      <div id= "niveauFacileScore" class="texte-meilleur">Meilleur: -</div>
    </button>
    <br>
    <button id="niveauMoyen" class="level-button menu-element" \
    onclick="selectionMode(16)">
      <b>Moyen</b>
      <br>
      <div id= "niveauMoyenScore" class="texte-meilleur">Meilleur: -</div>
    </button>
    <br>
    <button id="niveauDifficile" class="level-button menu-element" \
    onclick="selectionMode(20)">
      <b>Difficile</b>
      <br>
      <div id= "niveauDifficileScore" class="texte-meilleur">Meilleur: -</div>
    </button>
  </div>
</div>
'''


modeFacile='''      <tr>
        <td id="carte0" style="" onclick="clic(0)"></td>
        <td id="carte1" style="" onclick="clic(1)"></td>
        <td id="carte2" style="" onclick="clic(2)"></td>
        <td id="carte3" style="" onclick="clic(3)"></td>
      </tr>
      <tr>
        <td id="carte4" style="" onclick="clic(4)"></td>
        <td id="carte5" style="" onclick="clic(5)"></td>
        <td id="carte6" style="" onclick="clic(6)"></td>
        <td id="carte7" style="" onclick="clic(7)"></td>
      </tr>
      <tr>
        <td id="carte8" style="" onclick="clic(8)"></td>
        <td id="carte9" style="" onclick="clic(9)"></td>
        <td id="carte10" style="" onclick="clic(10)"></td>
        <td id="carte11" style="" onclick="clic(11)"></td>
      </tr>'''

modeMoyen='''<tr>
        <td id="carte12" style="" onclick="clic(12)"></td>
        <td id="carte13" style="" onclick="clic(13)"></td>
        <td id="carte14" style="" onclick="clic(14)"></td>
        <td id="carte15" style="" onclick="clic(15)"></td>
      </tr>'''
modeDifficile='''<tr>
        <td id="carte16" style="" onclick="clic(16)"></td>
        <td id="carte17" style="" onclick="clic(17)"></td>
        <td id="carte18" style="" onclick="clic(18)"></td>
        <td id="carte19" style="" onclick="clic(19)"></td>
      </tr>'''

tableauJeu  = []
tableauDeja = []
tableauTemporaireUrl = []
tableauTemporaireId  = []
score = 0
highscoreFacile = 0
highscoreMoyen = 0
highscoreDifficile = 0
niveau = 0
accumulateur = 0
globalSize = 0

def banqueImages():
    banqueImg = []
    for index in range(20):
        elementLink=document.querySelector("#link"+str(index))
        url=elementLink.getAttribute("href")
        banqueImg.append(url)
    return banqueImg

def selectionCartesAJouer(size):
    tabCartesAJouer=[]
    tableauTemporaire=[]
    banque=banqueImages()
    for placement in range(int(size/2)):
        x=math.floor(random()*len(banque))
        for _ in range(2):
            tableauTemporaire.append(banque[x])
        banque.pop(x)
    for iteration in range(size):
        index=math.floor(random()*len(tableauTemporaire))
        tabCartesAJouer.append(tableauTemporaire[index])
        tableauTemporaire.pop(index)
    return tabCartesAJouer

def selectionMode(size):
    global globalSize
    global tableauDeja
    global accumulateur
    global score
    global modeFacile
    global modeMoyen
    global modeDifficile
    global tableauJeu
    global niveau
    
    globalSize = size
    tableauDeja.clear()
    accumulateur = 0
    score = 100
    manipInnerHTML('#score-text',str(score))
    tabNiveaux=["#niveauFacile","#niveauMoyen","#niveauDifficile"]
    tabNivScore=["#niveauFacileScore","#niveauMoyenScore",
                 "#niveauDifficileScore"]
    if size==12:
        manipInnerHTML("#jeu",modeFacile)
        manipSetAttribute(tabNiveaux[0],"style","opacity:1 !important;")
        tabNiveaux.pop(0)
        
        for identif in tabNiveaux:
            manipSetAttribute(identif,"style","")
        niveau=tabNivScore[0]
        
    elif size==16:
        manipInnerHTML("#jeu",modeFacile+modeMoyen)
        manipSetAttribute(tabNiveaux[1],"style","opacity:1 !important;")
        tabNiveaux.pop(1)
        for identif in tabNiveaux:
            manipSetAttribute(identif,"style","")
        niveau=tabNivScore[1]
    
    else:
        manipInnerHTML("#jeu",modeFacile+modeMoyen+modeDifficile)
        manipSetAttribute(tabNiveaux[2],"style","opacity:1 !important;")
        tabNiveaux.pop(2)
        for identif in tabNiveaux:
            manipSetAttribute(identif,"style","")
        niveau=tabNivScore[2]
        
    tableauJeu=selectionCartesAJouer(size)   

def clic(index):
    global tableauJeu
    identificateur = "#carte"+str(index)
    url=tableauJeu[index]
    balise="background-image:url("
    manipSetAttribute(identificateur,"style",balise+url+")")
    comparaison(url, identificateur)

def comparaison(url, identificateur):
    global tableauDeja
    global tableauTemporaireUrl
    global tableauTemporaireId
    global score
    global highscoreFacile
    global highscoreMoyen
    global highscoreDifficile
    global accumulateur
    global globalSize
    global niveau
    
    tableauTemporaireUrl.append(url)
    tableauTemporaireId.append(identificateur)
    
    if len(tableauTemporaireUrl) == 2:
        if tableauTemporaireUrl[0] == tableauTemporaireUrl[1]:
            accumulateur += 2
        
            for i in range(2): 
                # Enlever le onClick des images quand une paire est trouvee
                manipSetAttribute(tableauTemporaireId[i],"onclick","")
            tableauTemporaireUrl.clear()
            tableauTemporaireId.clear()
            
        
        else:
        # Verifier si un des deux id est dans tableauDeja
        # Mettre les deux index dans tableauDeja
            dejaChoisi = False
            for i in range(2):
                if tableauTemporaireId[i] in tableauDeja: dejaChoisi = True
                
                else: tableauDeja.append(tableauTemporaireId[i])
                
            if dejaChoisi == True:
                if score != 0:
                    score -= 5
                    manipInnerHTML('#score-text',str(score))
                    manipSetAttribute("#score-box","style",
                                      "background: #ff3838")
            sleep(0.5)
            manipSetAttribute("#score-box","style","")
            
            for i in range(2):
                manipSetAttribute(tableauTemporaireId[i],"style","")
            tableauTemporaireUrl.clear()
            tableauTemporaireId.clear()
        
    if accumulateur == globalSize:
        if globalSize == 12: highscore = highscoreFacile
        elif globalSize == 16: highscore = highscoreMoyen
        else: highscore = highscoreDifficile
            
        if score > highscore: manipInnerHTML(niveau,"Meilleur: "+ str(score))
        sleep(0.1)
        alert("Bravo Miaou")
        # Animation de victoire
        sleep(5)
        if niveau == "#niveauFacileScore": selectionMode(12)
        elif niveau == "#niveauMoyenScore":selectionMode(16)
        else: selectionMode(20)
        
        
    # Clear les deux tableaux temporaires
    

def manipInnerHTML(id,contenu):
#Fonction qui permet de modifier le contenu InnerHTML du element, 
#en prenant son id et le contenu
    element=document.querySelector(id)
    element.innerHTML=contenu
    
def manipSetAttribute(id,attribut,valeur):
#Fonction qui permet de modifier la valeur d'un attribut d'un element HTML,
#en prenant en entree, le id de l'element, l'attribut a modifier et la valeur.
    element=document.querySelector(id)
    element.setAttribute(attribut,valeur)
       
manipInnerHTML('#main',html) #Chargement de la page html     
selectionMode(12)            #Onload commence en mode facile
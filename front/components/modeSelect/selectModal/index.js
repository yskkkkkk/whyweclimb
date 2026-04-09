import { useEffect, useState } from 'react';
import style from './selectModal.module.css';
import axios from 'axios';
import { motion } from 'framer-motion';
import toast from 'react-hot-toast';
import { useLanguage } from '../../../context/LanguageContext';

export default function SkinSelectModal({handleClose}) {
  const { t } = useLanguage();

  const UNLOCK_HINTS = {
    1: t('default_skin'),
    2: t('reach_level_3'),
    3: t('reach_level_6'),
    4: t('clear_game'),
  };
  const [currentSkin, setCurrentSkin] = useState(null);
  const [unlockedSkins, setUnlockedSkins] = useState([1]);

  useEffect(() => {
    const token = sessionStorage.getItem("token");
    const headers = { "Authorization": token };

    axios.get(`${process.env.NEXT_PUBLIC_API_URL}/user/information`, { headers })
      .then(res => setCurrentSkin(res.data.skinSeq))
      .catch(err => console.error(err));

    axios.get(`${process.env.NEXT_PUBLIC_API_URL}/user/skins`, { headers })
      .then(res => setUnlockedSkins(res.data))
      .catch(() => setUnlockedSkins([1]));
  }, []);

  const saveCharacter = (num) => {
    if (!unlockedSkins.includes(num)) {
      toast.error(`${t('unlock_condition')}${UNLOCK_HINTS[num]}`);
      return;
    }
    axios({
      url: `${process.env.NEXT_PUBLIC_API_URL}/user/skin`,
      method: 'PATCH',
      headers: { "Authorization": sessionStorage.getItem("token") },
      data: { skinSeq: num }
    }).then(() => {
      setCurrentSkin(num);
      toast.success("Character saved!");
      handleClose();
    }).catch(err => console.error(err));
  };

  const popUp = {
    initial:  { y: "-30vh", opacity: 0 },
    visible:  { y: "0", opacity: 1, transition: { duration: 0.1, type: "spring", damping: 25, stiffness: 500 } },
    exit:     { y: "-30vh", opacity: 0, transition: { duration: 0.2 } }
  };

  return (
    <motion.div
      onClick={(e) => e.stopPropagation()}
      variants={popUp}
      initial="initial"
      animate="visible"
      exit="exit"
    >
      <main className={style.container}>
        <h2>{t('character_select')}</h2>
        <div className={style.inModal}>
          {[1, 2, 3, 4].map(num => {
            const unlocked = unlockedSkins.includes(num);
            return (
              <div key={num} className={style.skinWrapper}>
                <a
                  className={`${style.imageA} ${currentSkin === num ? style.selected : ''} ${!unlocked ? style.locked : ''}`}
                  onClick={() => saveCharacter(num)}
                  title={unlocked ? '' : `🔒 ${UNLOCK_HINTS[num]}`}
                >
                  <img
                    className={style.characters}
                    src={`/images/${num}/running_R1.png`}
                    alt={`character ${num}`}
                  />
                  {!unlocked && <span className={style.lockOverlay}>🔒</span>}
                </a>
                <span className={style.hint}>{unlocked ? (currentSkin === num ? `✓ ${t('equipped')}` : '') : UNLOCK_HINTS[num]}</span>
              </div>
            );
          })}
        </div>
        <button className={style.backBtn} onClick={handleClose}>{t('back')}</button>
      </main>
    </motion.div>
  );
}

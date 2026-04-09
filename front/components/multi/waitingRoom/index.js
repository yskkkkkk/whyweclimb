import { useState } from 'react';
import style from './waitingRoom.module.css';
import { useLanguage } from '../../../context/LanguageContext';


export default function WaitingRoom({roomID, groupInfo, roomInfo, ready, startGame, goBack, stomp, userInfo, unlockedSkins}) {
  const { t } = useLanguage();
  const [showSkinPicker, setShowSkinPicker] = useState(false);

  const changeSkin = (skinSeq) => {
    if (!unlockedSkins || !unlockedSkins.includes(skinSeq)) return;
    stomp.send('/pub/room/skin', {}, JSON.stringify({
      userSeq: userInfo.userSeq,
      skinSeq
    }));
    setShowSkinPicker(false);
  };

  return (
    <>
      <main className={style.container}>
        <section className={style.headerContainer}>
          <header>welcome to room: {roomID}</header>
          <section className={style.status}>
            {groupInfo && groupInfo.length} / {roomInfo && roomInfo.roomMaxNum}
          </section>
        </section>

        <section>
          {groupInfo && groupInfo.slice(0).reverse().map((player, index) =>
            <div key={player.userSeq}>
              <img
                className={`player${index+1}`}
                src={`/images/waitRoomImg/${player.skinSeq}.png`}
                alt="character image"
              />
              <div className={`playerInfo${index+1}`}>
                {player.userId} - {player.ready ? t('ready') : t('not_ready')}
              </div>
            </div>
          )}
        </section>

        <section className={style.btns}>
          <button className={style.readyBtn} onClick={ready}>{t('ready')}</button>
          <button className={style.skinBtn} onClick={() => setShowSkinPicker(p => !p)}>skin</button>
          <button className={style.startBtn} onClick={startGame}>{t('start')}</button>
          <button className={style.backBtn} onClick={goBack}>{t('back')}</button>
        </section>

        {showSkinPicker && (
          <section className={style.skinPicker}>
            {[1, 2, 3, 4].map(num => {
              const unlocked = unlockedSkins && unlockedSkins.includes(num);
              const isCurrent = userInfo && groupInfo &&
                groupInfo.find(p => p.userSeq === userInfo.userSeq)?.skinSeq === num;
              return (
                <div
                  key={num}
                  className={`${style.skinOption} ${isCurrent ? style.skinCurrent : ''} ${!unlocked ? style.skinLocked : ''}`}
                  onClick={() => changeSkin(num)}
                >
                  <img src={`/images/waitRoomImg/${num}.png`} alt={`skin ${num}`} />
                  {!unlocked && <span className={style.lockIcon}>🔒</span>}
                </div>
              );
            })}
          </section>
        )}

        <section>
          <img className={style.closestCloud1} src="/images/cloud.svg" alt="cloud image" />
          <img className={style.closestCloud2} src="/images/cloud.svg" alt="cloud image" />
          <img className={style.closestCloud3} src="/images/cloud.svg" alt="cloud image" />
          <img className={style.closestCloud4} src="/images/cloud.svg" alt="cloud image" />
          <img className={style.closestCloud5} src="/images/cloud.svg" alt="cloud image" />

          <img className={style.closeCloud1} src="/images/cloud.svg" alt="cloud image" />
          <img className={style.closeCloud2} src="/images/cloud.svg" alt="cloud image" />
          <img className={style.closeCloud3} src="/images/cloud.svg" alt="cloud image" />

          <img className={style.farCloud1} src="/images/cloud.svg" alt="cloud image" />
          <img className={style.farCloud2} src="/images/cloud.svg" alt="cloud image" />
          <img className={style.farCloud3} src="/images/cloud.svg" alt="cloud image" />
          <img className={style.farCloud4} src="/images/cloud.svg" alt="cloud image" />
        </section>

        <section>
          <div className={style.wind1} />
          <div className={style.wind2} />
          <div className={style.wind3} />
          <div className={style.wind4} />
          <div className={style.wind5} />
          <div className={style.wind6} />
          <div className={style.wind7} />
          <div className={style.wind8} />
          <div className={style.wind9} />
          <div className={style.wind10} />
        </section>

      </main>
    </>
  );
}

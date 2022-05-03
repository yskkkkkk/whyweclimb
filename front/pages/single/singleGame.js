import dynamic from 'next/dynamic';
import style from './singleGame.module.css';
// 오직 cliend-side 에서만 렌더 되게끔 lazy loading 처리
const Engine = dynamic(() => { return import('../../components/engine') }, { ssr: false });

export default function SingleGame() {

  return (
    <main className={style.container}>
      <div className={style.head}>
        <p className={style.title}>점프컹스</p>
        <p id="mute">Mute<input type="checkbox" /></p>
      </div>
      <Engine />

    </main>
  )

}
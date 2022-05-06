import dynamic from 'next/dynamic';
import style from './singleGame.module.css';
import Link from 'next/link';
// 오직 cliend-side 에서만 렌더 되게끔 lazy loading 처리
const Engine = dynamic(() => { return import('../../components/engine') }, { ssr: false });

export default function SingleGame() {

  return (
    <main className={style.container}>
      <div className={style.head}>
        <p className={style.title}>Why We Climb</p>
        
          <p id="mute">Mute<input type="checkbox" /></p>
          <p className={style.time} id="time"></p>
        
      </div>
      <Engine/>
      <div className={style.buttons}>
        <Link href={'/'} passHref>
          <a><h3>Back</h3></a>
        </Link>
      </div>
    </main>
  )

}
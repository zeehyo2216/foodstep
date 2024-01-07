import { useRouter } from 'next/router';
import styles from './BottomNavigationBar.module.css';

const BottomNavigationBar = () => {
    const router = useRouter();

    const isActive = (path: string) => router.pathname === path;

    return (
        <div className={styles.bottomNavigationBar}>
            <div className={isActive('/') ? styles.activeNavItem : styles.navItem} onClick={() => router.push('/')}>
                Home
            </div>
            <div className={isActive('/about') ? styles.activeNavItem : styles.navItem} onClick={() => router.push('/about')}>
                About
            </div>
            <div className={isActive('/contact') ? styles.activeNavItem : styles.navItem} onClick={() => router.push('/contact')}>
                Contact
            </div>
        </div>
    );
};

export default BottomNavigationBar;